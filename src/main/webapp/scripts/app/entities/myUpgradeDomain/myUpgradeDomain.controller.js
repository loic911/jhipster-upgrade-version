'use strict';

angular.module('upgradeCheckApp')
    .controller('MyUpgradeDomainController', function ($scope, $state, $modal, MyUpgradeDomain, MyUpgradeDomainSearch, ParseLinks) {
      
        $scope.myUpgradeDomains = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            MyUpgradeDomain.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.myUpgradeDomains = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            MyUpgradeDomainSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.myUpgradeDomains = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.myUpgradeDomain = {
                name: null,
                myDate: null,
                id: null
            };
        };
    });
