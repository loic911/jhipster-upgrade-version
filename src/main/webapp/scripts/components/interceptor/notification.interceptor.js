 'use strict';

angular.module('upgradeCheckApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-upgradeCheckApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-upgradeCheckApp-params')});
                }
                return response;
            }
        };
    });
