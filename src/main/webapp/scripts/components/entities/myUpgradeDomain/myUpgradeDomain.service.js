'use strict';

angular.module('upgradeCheckApp')
    .factory('MyUpgradeDomain', function ($resource, DateUtils) {
        return $resource('api/myUpgradeDomains/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.myDate = DateUtils.convertDateTimeFromServer(data.myDate);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
