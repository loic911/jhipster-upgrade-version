'use strict';

angular.module('upgradeCheckApp')
    .factory('MyUpgradeDomainSearch', function ($resource) {
        return $resource('api/_search/myUpgradeDomains/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
