package com.njara.bounty.providers;

/**
 * Created by nfidiman on 04/02/2018.
 */

public interface IRealTimeDatabaseProvider {

    public void Write(String reference , Object object);

    Object Read(String reference ,  Class objectClass);

}
