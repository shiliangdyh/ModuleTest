package com.sl.componentbase;

import com.sl.componentbase.empty_service.EmptyLoginServiceIml;
import com.sl.componentbase.service.ILoginService;

public class ServiceFactory {
    private ILoginService loginService;

    private ServiceFactory() {
    }

    public static ServiceFactory getInstance() {
        return Inner.serviceFactory;
    }

    private static class Inner {
        private static final ServiceFactory serviceFactory = new ServiceFactory();
    }

    public void setAccountService(ILoginService accountService) {
        this.loginService = accountService;
    }

    public ILoginService getAccountService() {
        if (loginService == null) {
            loginService = new EmptyLoginServiceIml();
        }
        return loginService;
    }
}
