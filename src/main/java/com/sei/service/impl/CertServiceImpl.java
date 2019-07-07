package com.sei.service.impl;

import com.sei.bean.Cert;
import com.sei.dao.CertDao;
import com.sei.service.CertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CertServiceImpl implements CertService {
    @Autowired
    CertDao certDao;

    @Override
    public List<Cert> queryCert(String queryText) throws Exception {
        return certDao.queryCert(queryText);
    }
}
