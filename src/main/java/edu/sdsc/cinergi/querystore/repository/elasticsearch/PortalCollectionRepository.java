package edu.sdsc.cinergi.querystore.repository.elasticsearch;


import edu.sdsc.cinergi.querystore.model.PortalCollectionItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "metadatacollection", path = "collection")
public interface PortalCollectionRepository extends ElasticsearchRepository<PortalCollectionItem, String> {


    Page<PortalCollectionItem> findByUser(String user, Pageable pageable);
        Page<PortalCollectionItem> findByTitle(String name, Pageable pageable);

        @Query("{\"bool\": {\"must\": [{\"match\": {\"title\": \"?0\"}}]}}")
        Page<PortalCollectionItem> findByTitleUsingCustomQuery(String name, Pageable pageable);

   // PortalCollectionItem findOne(String id);
}

