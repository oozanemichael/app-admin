
package com.qkbus.modules.system.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageInfo;
import com.qkbus.common.service.impl.BaseServiceImpl;
import com.qkbus.common.utils.QueryHelpPlus;
import com.qkbus.config.FileProperties;
import com.qkbus.dozer.service.IGenerator;
import com.qkbus.exception.BadRequestException;
import com.qkbus.exception.EntityExistException;
import com.qkbus.modules.system.domain.Role;
import com.qkbus.modules.system.domain.User;
import com.qkbus.modules.system.domain.UsersRoles;
import com.qkbus.modules.system.service.UserService;
import com.qkbus.modules.system.service.UsersRolesService;
import com.qkbus.modules.system.service.dto.UserDto;
import com.qkbus.modules.system.service.dto.UserQueryCriteria;
import com.qkbus.modules.system.service.mapper.DeptMapper;
import com.qkbus.modules.system.service.mapper.JobMapper;
import com.qkbus.modules.system.service.mapper.RoleMapper;
import com.qkbus.modules.system.service.mapper.SysUserMapper;
import com.qkbus.utils.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;


/**
 * @author 少林一枝花
 * @date 2020-05-14
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserServiceImpl extends BaseServiceImpl<SysUserMapper, User> implements UserService {


    private final IGenerator generator;
    private final SysUserMapper userMapper;
    private final JobMapper jobMapper;
    private final DeptMapper deptMapper;
    private final RoleMapper roleMapper;
    private final RedisUtils redisUtils;
    private final UsersRolesService usersRolesService;
    private final FileProperties fileProperties;

    @Override

    public Map<String, Object> queryAll(UserQueryCriteria criteria, Pageable pageable) {
        getPage(pageable);
        PageInfo<User> page = new PageInfo<>(queryAll(criteria));
        Map<String, Object> map = new LinkedHashMap<>(2);
        map.put("content", generator.convert(page.getList(), UserDto.class));
        map.put("totalElements", page.getTotal());
        return map;
    }


    @Override
    public List<User> queryAll(UserQueryCriteria criteria) {
        List<User> userList = baseMapper.selectList(QueryHelpPlus.getPredicate(User.class, criteria));
        for (User user : userList) {
            user.setJob(jobMapper.selectById(user.getJobId()));
            user.setDept(deptMapper.selectById(user.getDeptId()));
            user.setRoles(roleMapper.findByUsers_Id(user.getId()));
        }
        return userList;
    }


    @Override
    public void download(List<UserDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (UserDto user : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("邮箱", user.getEmail());
            map.put("状态：1启用、0禁用", user.getEnabled());
            map.put("密码", user.getPassword());
            map.put("用户名", user.getUsername());
            map.put("部门名称", user.getDeptId());
            map.put("手机号码", user.getPhone());
            map.put("创建日期", user.getGmtCreate());
            map.put("最后修改密码的日期", user.getLastPasswordResetTime());
            map.put("昵称", user.getNickName());
            map.put("性别", user.getSex());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    /**
     * 根据用户名查询
     *
     * @param userName /
     * @return /
     */
    @Override
    public UserDto findByName(String userName) {
        User user = this.query().eq("username", userName).one();
        if (Objects.isNull(user)) {
            throw new BadRequestException("用户不存在");
        }
        //用户所属岗位
        user.setJob(jobMapper.selectById(user.getJobId()));
        //用户所属部门
        user.setDept(deptMapper.selectById(user.getDeptId()));
        return generator.convert(user, UserDto.class);
    }

    /**
     * 修改密码
     *
     * @param username        用户名
     * @param encryptPassword 密码
     */
    @Override
    public void updatePass(String username, String encryptPassword) {
        userMapper.updatePass(encryptPassword, DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"), username);
    }

    /**
     * 修改头像
     *
     * @param multipartFile 文件
     */
    @Override
    public Map<String, String> updateAvatar(MultipartFile multipartFile) {
        User user = this.getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, SecurityUtils.getUsername()));
        String oldPath = user.getAvatarPath();
        File file = FileUtil.upload(multipartFile, fileProperties.getPath().getAvatar());
        User newUser = new User();
        newUser.setId(user.getId());
        newUser.setAvatarPath(Objects.requireNonNull(file).getPath());
        newUser.setAvatar(file.getName());
        this.updateById(newUser);
        if (StringUtils.isNotBlank(oldPath)) {
            FileUtil.del(oldPath);
        }
        return new HashMap<String, String>(1) {{
            put("avatar", file.getName());
        }};
    }

    /**
     * 修改邮箱
     *
     * @param username 用户名
     * @param email    邮箱
     */
    @Override
    public void updateEmail(String username, String email) {
        userMapper.updateEmail(email, username);
    }

    /**
     * 新增用户
     *
     * @param resources /
     * @return /
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean create(User resources) {
        User userName = this.getOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, resources.getUsername()));
        if (userName != null) {
            throw new EntityExistException(User.class, "username", resources.getUsername());
        }
        User userEmail = this.getOne(new LambdaQueryWrapper<User>()
                .eq(User::getEmail, resources.getEmail()));
        if (userEmail != null) {
            throw new EntityExistException(User.class, "email", resources.getEmail());
        }
        resources.setDeptId(resources.getDept().getId());
        resources.setJobId(resources.getJob().getId());
        boolean result = this.save(resources);
        UsersRoles usersRoles = new UsersRoles();
        usersRoles.setUserId(resources.getId());
        Set<Role> set = resources.getRoles();
        for (Role roleIds : set) {
            usersRoles.setRoleId(roleIds.getId());
        }
        if (result) {
            usersRolesService.save(usersRoles);
        }
        return result;
    }

    /**
     * 编辑用户
     *
     * @param resources /
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(User resources) {
        User user = this.getOne(new LambdaQueryWrapper<User>()
                .eq(User::getId, resources.getId()));
        ValidationUtil.isNull(user.getId(), "User", "id", resources.getId());
        User user1 = this.getOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, resources.getUsername()));
        User user2 = this.getOne(new LambdaQueryWrapper<User>()
                .eq(User::getEmail, resources.getEmail()));

        if (user1 != null && !user.getId().equals(user1.getId())) {
            throw new BadRequestException("当前用户名已存在");
        }

        if (user2 != null && !user.getId().equals(user2.getId())) {
            throw new EntityExistException(User.class, "email", resources.getEmail());
        }
        user.setUsername(resources.getUsername());
        user.setEmail(resources.getEmail());
        user.setEnabled(resources.getEnabled());
        user.setDeptId(resources.getDept().getId());
        user.setJobId(resources.getJob().getId());
        user.setPhone(resources.getPhone());
        user.setNickName(resources.getNickName());
        user.setSex(resources.getSex());
        boolean result = this.saveOrUpdate(user);
        usersRolesService.lambdaUpdate().eq(UsersRoles::getUserId, resources.getId()).remove();
        UsersRoles usersRoles = new UsersRoles();
        usersRoles.setUserId(resources.getId());
        Set<Role> set = resources.getRoles();
        for (Role roleIds : set) {
            usersRoles.setRoleId(roleIds.getId());
        }
        if (result) {
            usersRolesService.save(usersRoles);
        }

        // 如果用户的角色改变了，需要手动清理下缓存
        if (!resources.getRoles().equals(user.getRoles())) {
            String key = "role::loadPermissionByUser:" + user.getUsername();
            redisUtils.del(key);
            key = "role::findByUsers_Id:" + user.getId();
            redisUtils.del(key);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> ids) {
        for (Long id : ids) {
            usersRolesService.lambdaUpdate().eq(UsersRoles::getUserId, id).remove();
        }
        this.removeByIds(ids);
    }

}
