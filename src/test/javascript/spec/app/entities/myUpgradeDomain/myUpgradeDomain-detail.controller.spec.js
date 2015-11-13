'use strict';

describe('MyUpgradeDomain Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockMyUpgradeDomain;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockMyUpgradeDomain = jasmine.createSpy('MockMyUpgradeDomain');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'MyUpgradeDomain': MockMyUpgradeDomain
        };
        createController = function() {
            $injector.get('$controller')("MyUpgradeDomainDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'upgradeCheckApp:myUpgradeDomainUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
