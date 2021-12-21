package com.qkbus.modules.member.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.qkbus.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author 少林一枝花
 * @date 2021-05-06
 */
@Data
@TableName("member")
public class Member extends BaseEntity implements Serializable {
    @TableId
    @ApiModelProperty(value = "自增ID")
    private Long id;

    @ApiModelProperty(value = "用户ID")
    private Long uid;

    @ApiModelProperty(value = "会员账号")
    private String account;

    @ApiModelProperty(value = "会员密码")
    private String password;

    @ApiModelProperty(value = "支付密码")
    private String payPassword;

    @ApiModelProperty(value = "会员昵称")
    private String nickName;

    @ApiModelProperty(value = "绑定邮箱")
    private String email;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "会员头像")
    private String avatar;

    @ApiModelProperty(value = "邀请码")
    private String inviteCode;

    @ApiModelProperty(value = "个人签名")
    private String personSign;


    @ApiModelProperty(value = "会员状态")
    private Integer status;

    @ApiModelProperty(value = "6位数的盐值")
    private String salt;

    @ApiModelProperty(value = "直推人数")
    private Integer inviteCount;

    @ApiModelProperty(value = "团队人数")
    private Integer teamCount;

    @ApiModelProperty(value = "有效直推人数")
    private Integer validInviteCount;

    @ApiModelProperty(value = "有效团队人数")
    private Integer validTeamCount;

    @ApiModelProperty(value = "设置等级")
    private Integer adminGrade;

    @ApiModelProperty(value = "用户等级")
    @TableField(value = "`grade`")
    private Integer grade;


    @ApiModelProperty(value = "乐观锁")
    private Integer version;

    @ApiModelProperty(value = "是否有效")
    private Integer validStatus;

    @ApiModelProperty(value = "试用期")
    private Integer isTrial;

    @ApiModelProperty(value = "密码")
    @TableField(exist = false)
    private String pwd;

    @ApiModelProperty(value = "支付密码")
    @TableField(exist = false)
    private String payPwd;

    @ApiModelProperty(value = "等级个数")
    @TableField(exist = false)
    private Integer gradeNum;
    /**
     * 认证状态
     */
    @ApiModelProperty(value = "认证状态")
    private Integer idEntityStatus;

    /**
     * 地址
     */
    @ApiModelProperty(value = "地址")
    private String address;
    /**
     * 助记词
     */
    @ApiModelProperty(value = "助记词")
    private String words;
    /**
     * 私钥
     */
    @ApiModelProperty(value = "私钥")
    private String privateKey;

    public void copy(Member source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
