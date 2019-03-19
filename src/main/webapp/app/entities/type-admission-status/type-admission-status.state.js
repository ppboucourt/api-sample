(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('type-admission-status', {
            parent: 'entity',
            url: '/type-admission-status',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TypeAdmissionStatuses'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-admission-status/type-admission-statuses.html',
                    controller: 'TypeAdmissionStatusController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('type-admission-status-detail', {
            parent: 'entity',
            url: '/type-admission-status/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TypeAdmissionStatus'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-admission-status/type-admission-status-detail.html',
                    controller: 'TypeAdmissionStatusDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'TypeAdmissionStatus', function($stateParams, TypeAdmissionStatus) {
                    return TypeAdmissionStatus.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'type-admission-status',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('type-admission-status-detail.edit', {
            parent: 'type-admission-status-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-admission-status/type-admission-status-dialog.html',
                    controller: 'TypeAdmissionStatusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TypeAdmissionStatus', function(TypeAdmissionStatus) {
                            return TypeAdmissionStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('type-admission-status.new', {
            parent: 'type-admission-status',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-admission-status/type-admission-status-dialog.html',
                    controller: 'TypeAdmissionStatusDialogController',
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
                    $state.go('type-admission-status', null, { reload: 'type-admission-status' });
                }, function() {
                    $state.go('type-admission-status');
                });
            }]
        })
        .state('type-admission-status.edit', {
            parent: 'type-admission-status',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-admission-status/type-admission-status-dialog.html',
                    controller: 'TypeAdmissionStatusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TypeAdmissionStatus', function(TypeAdmissionStatus) {
                            return TypeAdmissionStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-admission-status', null, { reload: 'type-admission-status' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('type-admission-status.delete', {
            parent: 'type-admission-status',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-admission-status/type-admission-status-delete-dialog.html',
                    controller: 'TypeAdmissionStatusDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TypeAdmissionStatus', function(TypeAdmissionStatus) {
                            return TypeAdmissionStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-admission-status', null, { reload: 'type-admission-status' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
