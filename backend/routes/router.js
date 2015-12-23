var express = require('express');
var router = express.Router();
var userController = require('../controllers/users.js');
var ideaController = require('../controllers/ideas.js');
var clusterController = require('../controllers/clusters.js');
var authController = require('../controllers/authcontroller.js');

//Users API endpoint
router.route('/users/register')
    .get(userController.getUsers)
    .post(userController.createUser);

router.route('/users/login')
    .post(userController.authUser);

router.route('/users/clusters')
    .get(authController.authToken, clusterController.getClusters)
    .post(authController.authToken, clusterController.postCluster)
    .delete(authController.authToken, clusterController.deleteCluster);

router.route('/users/ideas')
    .get(authController.authToken, ideaController.getIdeas)
    .post(authController.authToken, ideaController.postIdea)
    .delete(authController.authToken, ideaController.deleteIdea);

module.exports = router;
