'use strict';

angular.module('upgradeCheckApp').controller('MyUpgradeDomainDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'MyUpgradeDomain',
        function($scope, $stateParams, $modalInstance, entity, MyUpgradeDomain) {

        $scope.myUpgradeDomain = entity;
        $scope.load = function(id) {
            MyUpgradeDomain.get({id : id}, function(result) {
                $scope.myUpgradeDomain = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('upgradeCheckApp:myUpgradeDomainUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.myUpgradeDomain.id != null) {
                MyUpgradeDomain.update($scope.myUpgradeDomain, onSaveFinished);
            } else {
                MyUpgradeDomain.save($scope.myUpgradeDomain, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
