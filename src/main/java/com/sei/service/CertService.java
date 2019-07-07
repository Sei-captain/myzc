package com.sei.service;

import com.sei.bean.Cert;

import java.util.List;

public interface CertService {

    List<Cert> queryCert(String queryText)throws Exception;
}
