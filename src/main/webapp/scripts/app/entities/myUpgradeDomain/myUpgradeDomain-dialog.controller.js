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

        var onSaveSuccess = function (result) {
            $scope.$emit('upgradeCheckApp:myUpgradeDomainUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.myUpgradeDomain.id != null) {
                MyUpgradeDomain.update($scope.myUpgradeDomain, onSaveSuccess, onSaveError);
            } else {
                MyUpgradeDomain.save($scope.myUpgradeDomain, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
