package com.xiaojie.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class ApiProperties {

    public static String hostUrl;

    public static String branchName;
    

    @Value("${api.host_url}")
    public void setHostUrl(String hostUrl) {
        ApiProperties.hostUrl = hostUrl;
    }
    @Value("${api.branch_name}")
    public void setBranchName(String branchName) {
        ApiProperties.branchName = branchName;
    }
    


    

    
}
