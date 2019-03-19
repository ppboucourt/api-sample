(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('type-speciality', {
            parent: 'entity',
            url: '/type-speciality',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TypeSpeciality'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-speciality/type-speciality.html',
                    controller: 'TypeSpecialityController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('type-speciality-detail', {
            parent: 'entity',
            url: '/type-speciality/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TypeSpeciality'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-speciality/type-speciality-detail.html',
                    controller: 'TypeSpecialityDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'TypeSpeciality', function($stateParams, TypeSpeciality) {
                    return TypeSpeciality.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'type-speciality',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('type-speciality-detail.edit', {
            parent: 'type-speciality-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-speciality/type-speciality-dialog.html',
                    controller: 'TypeSpecialityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TypeSpeciality', function(TypeSpeciality) {
                            return TypeSpeciality.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('type-speciality.new', {
            parent: 'type-speciality',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-speciality/type-speciality-dialog.html',
                    controller: 'TypeSpecialityDialogController',
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
                    $state.go('type-speciality', null, { reload: 'type-speciality' });
                }, function() {
                    $state.go('type-speciality');
                });
            }]
        })
        .state('type-speciality.edit', {
            parent: 'type-speciality',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-speciality/type-speciality-dialog.html',
                    controller: 'TypeSpecialityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TypeSpeciality', function(TypeSpeciality) {
                            return TypeSpeciality.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-speciality', null, { reload: 'type-speciality' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('type-speciality.delete', {
            parent: 'type-speciality',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-speciality/type-speciality-delete-dialog.html',
                    controller: 'TypeSpecialityDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TypeSpeciality', function(TypeSpeciality) {
                            return TypeSpeciality.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-speciality', null, { reload: 'type-speciality' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
