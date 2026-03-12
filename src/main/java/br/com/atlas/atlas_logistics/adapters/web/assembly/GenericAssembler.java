package br.com.atlas.atlas_logistics.adapters.web.assembly;



import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import br.com.atlas.atlas_logistics.adapters.web.controller.CrudInterface;
import br.com.atlas.atlas_logistics.adapters.web.controller.ProductController;
import br.com.atlas.atlas_logistics.infrastructure.security.config.SecurityConfig;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


import java.util.*;
import java.util.function.Function;

@Component
public class GenericAssembler {

    private SecurityConfig securityConfig;

    public GenericAssembler(SecurityConfig securityConfig) {
        this.securityConfig = securityConfig;
    }


    public <c extends CrudInterface<?,?,listResponse,listResponseItems,returnOneResponse,id>,listResponse,listResponseItems,returnOneResponse,id>EntityModel<returnOneResponse>
    toModel(returnOneResponse dto,Class<c> controller, UUID id){

        EntityModel<returnOneResponse> model = EntityModel.of(dto);
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        Collection<? extends GrantedAuthority> reachableGrantedAuthorities= securityConfig.roleHierarchy().getReachableGrantedAuthorities(authorities);


        int defaultPageNumber = 0;
        int defaultPageItemsCapability = 10;

        if (hasAuthority(reachableGrantedAuthorities,"AUDITOR")){
            model.add(linkTo(methodOn(controller).getAll(defaultPageNumber,defaultPageItemsCapability)).withRel("complete list"));
            model.add(linkTo(methodOn(controller).getOne(id)).withRel("specific return"));

        }

        if(hasAuthority(reachableGrantedAuthorities,"INVENTORY")){
            model.add(linkTo(methodOn(controller).patch(id,null)).withRel("partial update"));

        }

        if(hasAuthority(reachableGrantedAuthorities, "OPERATOR")) {
            model.add(linkTo(methodOn(controller).save(null)).withRel("create"));
            model.add(linkTo(methodOn(controller).update(id,null)).withRel("complete update"));

        }

        if(hasAuthority(reachableGrantedAuthorities, "ADMIN")) {
            model.add(linkTo(methodOn(controller).delete(id)).withRel("delete"));
        }


        return model;

    }


    public <c extends CrudInterface<?,?, listResponse,listResponseItems,returnOneResponse,id>,listResponse,listResponseItems,returnOneResponse,id>CollectionModel<EntityModel<listResponseItems>>
    toListModel(List<listResponseItems> dto,Class<c> controller,Function<listResponseItems, id> extractId){

        int defaultPageNumber = 0;
        int defaultPageItemsCapability = 10;

        List<EntityModel<listResponseItems>> modelList = new ArrayList<>();


        for(listResponseItems lr : dto){
            id objectId = extractId.apply(lr);
            EntityModel<listResponseItems> model = EntityModel.of(lr);
            model.add(linkTo(methodOn(controller).getOne((UUID) objectId)).withSelfRel());

            modelList.add(model);
        }


        return CollectionModel.of(modelList,linkTo(methodOn(controller).getAll(defaultPageNumber,defaultPageItemsCapability)).withSelfRel());

    }


    private boolean hasAuthority (Collection<? extends GrantedAuthority> reacheble, String authority){

        return reacheble.stream().anyMatch(a -> a.getAuthority().equals(authority));
    }








}

