'use strict';

angular.module('upgradeCheckApp')
    .controller('MyUpgradeDomainDetailController', function ($scope, $rootScope, $stateParams, entity, MyUpgradeDomain) {
        $scope.myUpgradeDomain = entity;
        $scope.load = function (id) {
            MyUpgradeDomain.get({id: id}, function(result) {
                $scope.myUpgradeDomain = result;
            });
        };
        var unsubscribe = $rootScope.$on('upgradeCheckApp:myUpgradeDomainUpdate', function(event, result) {
            $scope.myUpgradeDomain = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
