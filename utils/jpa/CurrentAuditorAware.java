package com.noya.rbac.common.config;

import com.noya.common.Constants;
import com.noya.rbac.account.AccountsDto;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 支持createBy等注解
 */
@Configuration
public class CurrentAuditorAware implements AuditorAware<String> {

    @Override
    public String getCurrentAuditor() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession(false);
        if(session==null){
            return "sys";
        }
        AccountsDto accountsSession = (AccountsDto) session.getAttribute(Constants.LOGIN_ACCOUNT_SESSION);
        return accountsSession!=null?accountsSession.getSysId():"sys";
    }
}
