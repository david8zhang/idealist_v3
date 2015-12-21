var express = require('express');
var router = express.Router();
var userController = require('../controllers/users.js');
var ideaController = require('../controllers/ideas.js');
var clusterController = require('../controllers/clusters.js');


//Users API endpoint 
router.route('/users')
    .post(userController.postUser)
    .get(userController.getUsers);

router.route('/users/:id')
    .put(userController.putUser)
    .get(userController.getUser)
    .delete(userController.deleteUser);

router.route('/users/:id/clusters/:cluster_id)
    .put(clusterController.putCluster)
    .get(clusterController.getCluster)
    .post(clusterController.createCluster)
    .delete(clusteController.deleteCluster)
    .post(clusterController.postIdea);

module.exports = router;
