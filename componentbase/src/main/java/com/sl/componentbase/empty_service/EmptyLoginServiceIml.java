package com.sl.componentbase.empty_service;

import com.sl.componentbase.service.ILoginService;

public class EmptyLoginServiceIml implements ILoginService {
    @Override
    public boolean isLogin() {
        return false;
    }
}
