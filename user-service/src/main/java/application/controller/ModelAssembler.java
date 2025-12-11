package application.controller;

import application.model.dto.UserRequest;
import application.model.dto.UserResponse;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ModelAssembler implements RepresentationModelAssembler<UserResponse, EntityModel<UserResponse>> {
    @Override
    public EntityModel<UserResponse> toModel(UserResponse entity) {
        return EntityModel.of(entity, linkTo(methodOn(UserController.class).getUserById(entity.id())).withSelfRel()
        );
    }

    @Override
    public CollectionModel<EntityModel<UserResponse>> toCollectionModel(Iterable<? extends UserResponse> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities).add(linkTo(methodOn(UserController.class).getAll()).withSelfRel());
    }
}
