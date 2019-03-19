(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('type-person', {
            parent: 'entity',
            url: '/type-person',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TypePeople'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-person/type-people.html',
                    controller: 'TypePersonController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('type-person-detail', {
            parent: 'entity',
            url: '/type-person/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TypePerson'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-person/type-person-detail.html',
                    controller: 'TypePersonDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'TypePerson', function($stateParams, TypePerson) {
                    return TypePerson.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'type-person',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('type-person-detail.edit', {
            parent: 'type-person-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-person/type-person-dialog.html',
                    controller: 'TypePersonDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TypePerson', function(TypePerson) {
                            return TypePerson.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('type-person.new', {
            parent: 'type-person',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-person/type-person-dialog.html',
                    controller: 'TypePersonDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                status: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('type-person', null, { reload: 'type-person' });
                }, function() {
                    $state.go('type-person');
                });
            }]
        })
        .state('type-person.edit', {
            parent: 'type-person',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-person/type-person-dialog.html',
                    controller: 'TypePersonDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TypePerson', function(TypePerson) {
                            return TypePerson.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-person', null, { reload: 'type-person' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('type-person.delete', {
            parent: 'type-person',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-person/type-person-delete-dialog.html',
                    controller: 'TypePersonDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TypePerson', function(TypePerson) {
                            return TypePerson.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-person', null, { reload: 'type-person' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
