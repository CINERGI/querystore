package edu.sdsc.cinergi.querystore.repository.elasticsearch;


import edu.sdsc.cinergi.querystore.model.PortalCollectionItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(collectionResourceRel = "metadatacollection", path = "item")
public interface PortalCollectionRepository extends ElasticsearchRepository<PortalCollectionItem, String> {


    @RestResource(path = "user")
    Page<PortalCollectionItem> findByUser(String user, Pageable pageable);
    @RestResource(path = "title")
    Page<PortalCollectionItem> findByTitle(String title, Pageable pageable);

    @RestResource(path = "collections")
    @Query("{\"bool\": {\"must\": [{\"match\": {\"collections\": \"?0\"}}]}}")
    Page<PortalCollectionItem> findByCollectionsUsingCustomQuery(String collection, Pageable pageable);

        @Query("{\"bool\": {\"must\": [{\"match\": {\"title\": \"?0\"}}]}}")
        Page<PortalCollectionItem> findByTitleUsingCustomQuery(String name, Pageable pageable);

   // PortalCollectionItem findOne(String id);
}

