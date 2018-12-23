package com.noya.rbac.common.interceptor;

import com.noya.rbac.account.Accounts;
import com.noya.rbac.org.Company;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * jpa操作实体前的拦截器
 */
@Component
public class AutoSetIdInterceptor extends EmptyInterceptor {
    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        if(entity instanceof Accounts){
            Accounts entityFact = (Accounts)entity;
            for ( int i=0; i<propertyNames.length; i++ ) {
                if ( "accountId".equals( propertyNames[i] ) ) {
                    state[i] = entityFact.getSysId();
                    return true;
                }

            }
        }else if(entity instanceof Company){
            Company entityFact = (Company)entity;
            int ok = 0;
            for ( int i=0; i<propertyNames.length; i++ ) {
                if ( "sysCompanyId".equals( propertyNames[i] ) ) {
                    state[i] = entityFact.getSysId();
                    ok++;
                }
                if ( "registTime".equals( propertyNames[i] ) ) {
                    state[i] = entityFact.getSysCreateTime();
                    ok++;
                }
                if(ok==2){
                    return true;
                }
            }
        }
        return false;
    }
}
