'use strict';

angular.module('upgradeCheckApp')
	.controller('MyUpgradeDomainDeleteController', function($scope, $modalInstance, entity, MyUpgradeDomain) {

        $scope.myUpgradeDomain = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            MyUpgradeDomain.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });