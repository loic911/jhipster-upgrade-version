'use strict';

angular.module('upgradeCheckApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


