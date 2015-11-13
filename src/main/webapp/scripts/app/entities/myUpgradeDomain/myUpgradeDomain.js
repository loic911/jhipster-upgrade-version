'use strict';

angular.module('upgradeCheckApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('myUpgradeDomain', {
                parent: 'entity',
                url: '/myUpgradeDomains',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'upgradeCheckApp.myUpgradeDomain.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/myUpgradeDomain/myUpgradeDomains.html',
                        controller: 'MyUpgradeDomainController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('myUpgradeDomain');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('myUpgradeDomain.detail', {
                parent: 'entity',
                url: '/myUpgradeDomain/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'upgradeCheckApp.myUpgradeDomain.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/myUpgradeDomain/myUpgradeDomain-detail.html',
                        controller: 'MyUpgradeDomainDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('myUpgradeDomain');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'MyUpgradeDomain', function($stateParams, MyUpgradeDomain) {
                        return MyUpgradeDomain.get({id : $stateParams.id});
                    }]
                }
            })
            .state('myUpgradeDomain.new', {
                parent: 'myUpgradeDomain',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/myUpgradeDomain/myUpgradeDomain-dialog.html',
                        controller: 'MyUpgradeDomainDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    myDate: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('myUpgradeDomain', null, { reload: true });
                    }, function() {
                        $state.go('myUpgradeDomain');
                    })
                }]
            })
            .state('myUpgradeDomain.edit', {
                parent: 'myUpgradeDomain',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/myUpgradeDomain/myUpgradeDomain-dialog.html',
                        controller: 'MyUpgradeDomainDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['MyUpgradeDomain', function(MyUpgradeDomain) {
                                return MyUpgradeDomain.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('myUpgradeDomain', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
