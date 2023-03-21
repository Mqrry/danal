
package com.ksk.danal.pojo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "TID",
    "BackURL",
    "IsCharSet",
    "IsDstAddr",
    "IsExceptCarrier",
    "IsCarrier",
    "IsMobileW",
    "UsePNS",
    "ReferURL",
    "AGELIMIT",
    "IsNAME",
    "timestamp",
    "serverinfo",
    "UseCarriers",
    "dndata",
    "cpdata",
    "PageTitle",
    "CERTTYPE",
    "CERTUSE",
    "foreignAgree"
})
public class DanalStart {

    @JsonProperty("TID")
    private String tid;
    @JsonProperty("BackURL")
    private String backURL;
    @JsonProperty("IsCharSet")
    private String isCharSet;
    @JsonProperty("IsDstAddr")
    private String isDstAddr;
    @JsonProperty("IsExceptCarrier")
    private Object isExceptCarrier;
    @JsonProperty("IsCarrier")
    private String isCarrier;
    @JsonProperty("IsMobileW")
    private String isMobileW;
    @JsonProperty("UsePNS")
    private String usePNS;
    @JsonProperty("ReferURL")
    private String referURL;
    @JsonProperty("AGELIMIT")
    private Integer agelimit;
    @JsonProperty("IsNAME")
    private Object isNAME;
    @JsonProperty("timestamp")
    private Integer timestamp;
    @JsonProperty("serverinfo")
    private String serverinfo;
    @JsonProperty("UseCarriers")
    private List<String> useCarriers;
    @JsonProperty("dndata")
    private String dndata;
    @JsonProperty("cpdata")
    private Cpdata cpdata;
    @JsonProperty("PageTitle")
    private String pageTitle;
    @JsonProperty("CERTTYPE")
    private String certtype;
    @JsonProperty("CERTUSE")
    private String certuse;
    @JsonProperty("foreignAgree")
    private String foreignAgree;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<>();

    @JsonProperty("TID")
    public String getTid() {
        return tid;
    }

    @JsonProperty("TID")
    public void setTid(String tid) {
        this.tid = tid;
    }

    @JsonProperty("BackURL")
    public String getBackURL() {
        return backURL;
    }

    @JsonProperty("BackURL")
    public void setBackURL(String backURL) {
        this.backURL = backURL;
    }

    @JsonProperty("IsCharSet")
    public String getIsCharSet() {
        return isCharSet;
    }

    @JsonProperty("IsCharSet")
    public void setIsCharSet(String isCharSet) {
        this.isCharSet = isCharSet;
    }

    @JsonProperty("IsDstAddr")
    public String getIsDstAddr() {
        return isDstAddr;
    }

    @JsonProperty("IsDstAddr")
    public void setIsDstAddr(String isDstAddr) {
        this.isDstAddr = isDstAddr;
    }

    @JsonProperty("IsExceptCarrier")
    public Object getIsExceptCarrier() {
        return isExceptCarrier;
    }

    @JsonProperty("IsExceptCarrier")
    public void setIsExceptCarrier(Object isExceptCarrier) {
        this.isExceptCarrier = isExceptCarrier;
    }

    @JsonProperty("IsCarrier")
    public String getIsCarrier() {
        return isCarrier;
    }

    @JsonProperty("IsCarrier")
    public void setIsCarrier(String isCarrier) {
        this.isCarrier = isCarrier;
    }

    @JsonProperty("IsMobileW")
    public String getIsMobileW() {
        return isMobileW;
    }

    @JsonProperty("IsMobileW")
    public void setIsMobileW(String isMobileW) {
        this.isMobileW = isMobileW;
    }

    @JsonProperty("UsePNS")
    public String getUsePNS() {
        return usePNS;
    }

    @JsonProperty("UsePNS")
    public void setUsePNS(String usePNS) {
        this.usePNS = usePNS;
    }

    @JsonProperty("ReferURL")
    public String getReferURL() {
        return referURL;
    }

    @JsonProperty("ReferURL")
    public void setReferURL(String referURL) {
        this.referURL = referURL;
    }

    @JsonProperty("AGELIMIT")
    public Integer getAgelimit() {
        return agelimit;
    }

    @JsonProperty("AGELIMIT")
    public void setAgelimit(Integer agelimit) {
        this.agelimit = agelimit;
    }

    @JsonProperty("IsNAME")
    public Object getIsNAME() {
        return isNAME;
    }

    @JsonProperty("IsNAME")
    public void setIsNAME(Object isNAME) {
        this.isNAME = isNAME;
    }

    @JsonProperty("timestamp")
    public Integer getTimestamp() {
        return timestamp;
    }

    @JsonProperty("timestamp")
    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    @JsonProperty("serverinfo")
    public String getServerinfo() {
        return serverinfo;
    }

    @JsonProperty("serverinfo")
    public void setServerinfo(String serverinfo) {
        this.serverinfo = serverinfo;
    }

    @JsonProperty("UseCarriers")
    public List<String> getUseCarriers() {
        return useCarriers;
    }

    @JsonProperty("UseCarriers")
    public void setUseCarriers(List<String> useCarriers) {
        this.useCarriers = useCarriers;
    }

    @JsonProperty("dndata")
    public String getDndata() {
        return dndata;
    }

    @JsonProperty("dndata")
    public void setDndata(String dndata) {
        this.dndata = dndata;
    }

    @JsonProperty("cpdata")
    public Cpdata getCpdata() {
        return cpdata;
    }

    @JsonProperty("cpdata")
    public void setCpdata(Cpdata cpdata) {
        this.cpdata = cpdata;
    }

    @JsonProperty("PageTitle")
    public String getPageTitle() {
        return pageTitle;
    }

    @JsonProperty("PageTitle")
    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    @JsonProperty("CERTTYPE")
    public String getCerttype() {
        return certtype;
    }

    @JsonProperty("CERTTYPE")
    public void setCerttype(String certtype) {
        this.certtype = certtype;
    }

    @JsonProperty("CERTUSE")
    public String getCertuse() {
        return certuse;
    }

    @JsonProperty("CERTUSE")
    public void setCertuse(String certuse) {
        this.certuse = certuse;
    }

    @JsonProperty("foreignAgree")
    public String getForeignAgree() {
        return foreignAgree;
    }

    @JsonProperty("foreignAgree")
    public void setForeignAgree(String foreignAgree) {
        this.foreignAgree = foreignAgree;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
