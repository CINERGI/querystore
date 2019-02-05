package edu.sdsc.cinergi.querystore.model;

import lombok.Data;
import lombok.Builder;

import org.springframework.data.annotation.Id;


@Data
@Builder
public class DatasetReference {
    private @Id String id;
private String fileId;
private String reference;
private String url;
private String dataSetUrl;
private String datasetType;

//public DatasetReference() {}


//public DatasetReference(String fileid , String reference,String url, String datasetUrl, String datasetType){
//    this.fileId=fileid;
//    this.reference=reference;
//    this.url=url;
//    this.datasetType=datasetType;
//    this.dataSetUrl=datasetUrl;
//}
}
