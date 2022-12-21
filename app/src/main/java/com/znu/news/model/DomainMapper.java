package com.znu.news.model;

public interface DomainMapper<T, DomainModel> {

    DomainModel mapToDomainModel(T model);

    T mapFromDomainModel(DomainModel domainModel);
}
