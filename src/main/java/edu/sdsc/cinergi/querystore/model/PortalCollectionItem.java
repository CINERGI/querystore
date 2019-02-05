package edu.sdsc.cinergi.querystore.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.util.List;

import static org.springframework.data.elasticsearch.annotations.FieldType.Date;

@Data
@Builder
//@JsonDeserialize(builder = PortalCollectionItem.class)
//@Document(indexName = "querystore", type = "metadataCollection")
@Document(indexName = "querystore")
public class PortalCollectionItem {

    private String type; // ES6 no longer support multiple types.
    private @Id String id;
     private String title;

    private String user;
    private @Field(type = Date) String created;
    /* type=collection
    id
    title
    */
    private String parent;
    // for items with collections
    private List<String> collections; // for collection one item in list
    private List<String> collectionIdentifiers; // Use Id's so that we can rename collections


//    private @Field(type = Date) String modified;
//    private @Field(type = Date) String lastAccess;

//    @Field(type = FieldType.Nested, includeInParent = true)
//    private List<DatasetReference> datasetReferences;

    /* type=search

     */
    private String searchText;
    private String searchUrl;
    private String params;
    private String query;

    /* metadata item
    * type=metadata item
    * title
    * collections
     */
    private String fileId;
    private String mdlink;
    private String description;


    /* dateset url
     * type=dataset
     * fileid
     * mdlink
     *
     */
    private String reference;
    private String dataSetUrl;
    private String datasetType;


//    public PortalCollectionItem(){
//
//    }



    public static PortalCollectionItem createCollection(String id, String title, String user, String Created){
        PortalCollectionItem item =  PortalCollectionItem.builder()
                .type("colllection")
                .title(title)
                .user(user)
                .created(Created).build();

        return item;
    }
    public static PortalCollectionItem createSearchItem(String id, String title, String user, String Created){
        PortalCollectionItem item =  PortalCollectionItem.builder()
                .type("search")
                .title(title)
                .user(user)
                .created(Created).build();
        return item;
    }

    public static PortalCollectionItem createMetadataItem(String id, String title, String user, String Created){
        PortalCollectionItem item =  PortalCollectionItem.builder()
                .type("metadata")
                .title(title)
                .user(user)
                .created(Created).build();
        return item;
    }
    public static PortalCollectionItem createDatasetItem(String id, String title, String user, String Created){
        PortalCollectionItem item =  PortalCollectionItem.builder()
                .type("dataset")
                .title(title)
                .user(user)
                .created(Created).build();
        return item;
    }

//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getUser() {
//        return user;
//    }
//
//    public void setUser(String user) {
//        this.user = user;
//    }
//
//    public List<DatasetReference> getDatasetReferences() {
//        return datasetReferences;
//    }
//
//    public void setDatasetReferences(List<DatasetReference> datasetReferences) {
//        this.datasetReferences = datasetReferences;
//    }

    // standard getters and setters

}
