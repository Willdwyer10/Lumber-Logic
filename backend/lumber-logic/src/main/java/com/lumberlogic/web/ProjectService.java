package com.lumberlogic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public Project createProject(String info, String username, String password) {
        // For development purposes only! Using plaintext passwords.

        // Check if both username and password match in the database
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username)
                .and("password").is(password));

        // Execute the query
        User user = mongoTemplate.findOne(query, User.class);

        if (user != null) {
            // Both username and password match, proceed with project creation
            Project project = projectRepository.insert(new Project(info));

            // Update the user's projectIds
            mongoTemplate.update(User.class)
                    .matching(Criteria.where("username").is(username))
                    .apply(new Update().push("projectIds").value(project))
                    .first();

            return project;
        }

        // Invalid username or password
        throw new IllegalArgumentException("Invalid username or password");
    }

}
