package com.myblog7.controller;

import com.myblog7.payload.PostDto;
import com.myblog7.payload.PostResponse;
import com.myblog7.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/post")//it receives jsaon object from postman
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {//cpnstruction based dependency injection
        this.postService = postService;
    }//constructor based dependency injection

    //handler method \
    @PreAuthorize("hasRole('ADMIN')")//this perticular method can be accessed by only admin
    @PostMapping//@valid-only when this is valid the postdto checking will happen with spring validation and it will report error,to report error use class called bindingresult result
    public ResponseEntity<?> savePost(@Valid @RequestBody PostDto postDto, BindingResult result) {//request body will copy content from jason object to dto object,response entity will show postDto object in resp[onse section of postman
      if(result.hasErrors()){
          return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
      }
        PostDto dto = postService.savePost(postDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);//wheneved we save data in database the status code is 201 and received dto is shown in response section of database
    }
    @PreAuthorize("hasRole('ADMIN')")//this perticular method can be accessed by only admit
    @DeleteMapping("/{id}")//http://localhost:8080/api/post/1
    public ResponseEntity<String> deletePost(@PathVariable("id") long id){//return type is string,path variable take id and store it in long
        postService.deletePost(id);
        return new ResponseEntity<>("post is deleted",HttpStatus.OK);//200-delete,update,reading
    }
    @PreAuthorize("hasRole('ADMIN')")//this perticular method can be accessed by only admit
    @PutMapping("/{id}")//http://localhost:8080/api/post/1
    public ResponseEntity<PostDto>updatePost(@PathVariable ("id") long id,@RequestBody PostDto postDto){
        PostDto dto = postService.updatePost(id, postDto);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto>getPostById(@PathVariable("id")long id){
        PostDto dto = postService.getPostById(id);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
    @GetMapping//http://localhost:8080/api/post?pageNo=0&pageSize=3&sortBy=id&sortDir=asc//question mark is because we are using query parameter @requestparam
public PostResponse getPosts(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int
                    pageNo,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) int
                    pageSize
            ,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String
                    sortBy
            ,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String
                    sortDir
    ){
       PostResponse postResponse= postService.getPosts(pageNo,pageSize,sortBy,sortDir);
       return postResponse;
}
}
