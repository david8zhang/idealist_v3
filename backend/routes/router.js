var express = require('express');
var router = express.Router();
var userController = require('../controllers/users.js');
var ideaController = require('../controllers/ideas.js');
var clusterController = require('../controllers/clusters.js');

//Users API endpoint
router.route('/users')
    .get(userController.getUsers)
    .post(userController.postUser);

router.route('/users/clusters')
    .get(clusterController.getClusters)
    .post(clusterController.postCluster)
    .delete(clusterController.deleteCluster);

router.route('/users/ideas')
    .get(ideaController.getIdeas)
    .post(ideaController.postIdea)
    .delete(ideaController.deleteIdea);

module.exports = router;
