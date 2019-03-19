(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('type-pre-admission-status', {
            parent: 'entity',
            url: '/type-pre-admission-status',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TypePreAdmissionStatuses'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-pre-admission-status/type-pre-admission-statuses.html',
                    controller: 'TypePreAdmissionStatusController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('type-pre-admission-status-detail', {
            parent: 'entity',
            url: '/type-pre-admission-status/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TypePreAdmissionStatus'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-pre-admission-status/type-pre-admission-status-detail.html',
                    controller: 'TypePreAdmissionStatusDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'TypePreAdmissionStatus', function($stateParams, TypePreAdmissionStatus) {
                    return TypePreAdmissionStatus.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'type-pre-admission-status',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('type-pre-admission-status-detail.edit', {
            parent: 'type-pre-admission-status-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-pre-admission-status/type-pre-admission-status-dialog.html',
                    controller: 'TypePreAdmissionStatusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TypePreAdmissionStatus', function(TypePreAdmissionStatus) {
                            return TypePreAdmissionStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('type-pre-admission-status.new', {
            parent: 'type-pre-admission-status',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-pre-admission-status/type-pre-admission-status-dialog.html',
                    controller: 'TypePreAdmissionStatusDialogController',
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
                    $state.go('type-pre-admission-status', null, { reload: 'type-pre-admission-status' });
                }, function() {
                    $state.go('type-pre-admission-status');
                });
            }]
        })
        .state('type-pre-admission-status.edit', {
            parent: 'type-pre-admission-status',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-pre-admission-status/type-pre-admission-status-dialog.html',
                    controller: 'TypePreAdmissionStatusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TypePreAdmissionStatus', function(TypePreAdmissionStatus) {
                            return TypePreAdmissionStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-pre-admission-status', null, { reload: 'type-pre-admission-status' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('type-pre-admission-status.delete', {
            parent: 'type-pre-admission-status',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-pre-admission-status/type-pre-admission-status-delete-dialog.html',
                    controller: 'TypePreAdmissionStatusDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TypePreAdmissionStatus', function(TypePreAdmissionStatus) {
                            return TypePreAdmissionStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-pre-admission-status', null, { reload: 'type-pre-admission-status' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
