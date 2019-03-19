(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('type-employee', {
            parent: 'entity',
            url: '/type-employee',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TypeEmployees'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-employee/type-employees.html',
                    controller: 'TypeEmployeeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('type-employee-detail', {
            parent: 'entity',
            url: '/type-employee/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TypeEmployee'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-employee/type-employee-detail.html',
                    controller: 'TypeEmployeeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'TypeEmployee', function($stateParams, TypeEmployee) {
                    return TypeEmployee.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'type-employee',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('type-employee-detail.edit', {
            parent: 'type-employee-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-employee/type-employee-dialog.html',
                    controller: 'TypeEmployeeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TypeEmployee', function(TypeEmployee) {
                            return TypeEmployee.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('type-employee.new', {
            parent: 'type-employee',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-employee/type-employee-dialog.html',
                    controller: 'TypeEmployeeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('type-employee', null, { reload: 'type-employee' });
                }, function() {
                    $state.go('type-employee');
                });
            }]
        })
        .state('type-employee.edit', {
            parent: 'type-employee',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-employee/type-employee-dialog.html',
                    controller: 'TypeEmployeeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TypeEmployee', function(TypeEmployee) {
                            return TypeEmployee.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-employee', null, { reload: 'type-employee' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('type-employee.delete', {
            parent: 'type-employee',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-employee/type-employee-delete-dialog.html',
                    controller: 'TypeEmployeeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TypeEmployee', function(TypeEmployee) {
                            return TypeEmployee.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-employee', null, { reload: 'type-employee' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
