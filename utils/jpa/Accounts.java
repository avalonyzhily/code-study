package com.noya.rbac.account;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name="AAAccounts")
@SQLDelete(sql = "update AAAccounts set SysDelete = 1 where SysId = ?")
//@EntityListeners(AuditingEntityListener.class)
@Data
public class Accounts {
    @Id
//    @GeneratedValue(generator = "uuid")
//    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name="SysId")
    private String sysId;//'账户SysId'
    @Column(name="AccountId")
    private String accountId;// '账户编号',
    @Column(name="SysCompanyId")
    private String sysCompanyId;//'SysCompanyId',
    @Column(name="AccountCode")
    @NotBlank(message = "账号不能为空")
    @Size(max = 32)
    private String accountCode;//'登录账号',
    @Column(name="AccountName")
    @Size(max = 64)
    private String accountName;//'账户昵称',
    @Column(name="AccountPinYin")
    @Size(max = 64)
    private String accountPinYin;//'拼音简码',
    @Column(name="AccountPassword")
    @NotBlank(message = "密码不能为空")
    @Size(max = 64)
    private String accountPassword;//'登录密码',
    @Column(name="AccountEmail")
    @Size(max = 64)
    @Pattern(message = "邮箱格式不正确",regexp = "^\\w+((-\\w+)|(\\.\\w+))*@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$")
    private String accountEmail;//'安全邮箱',
    @Column(name="AccountMobile")
    @Pattern(message = "手机格式不正确",regexp = "^((13[0-9])|(15[^4,\\\\D])|(18[0,5-9]))\\d{8}$")
    private String accountMobile;//'安全手机',
    @Column(name="Remark")
    @Size(max = 256)
    private String remark;//'备注',
    @Column(name="SysCreateUserID")
//    @CreatedBy
    private String sysCreateUserID;//
//    @CreatedDate
    @Column(name="SysCreateTime",updatable = false)
    private Date sysCreateTime ;//
    @Column(name="SysUpdateUserID",updatable = false)
//    @LastModifiedBy
    private String sysUpdateUserID;//
    @Column(name="SysUpdateTime")
//    @LastModifiedDate
    private Date sysUpdateTime;//
    @Column(name="SysDelete")
    private Integer sysDelete;
}
