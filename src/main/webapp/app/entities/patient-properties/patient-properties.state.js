(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('patient-properties', {
            parent: 'entity',
            url: '/patient-properties',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Patient_properties'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/patient-properties/patient-properties.html',
                    controller: 'Patient_propertiesController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('patient-properties-detail', {
            parent: 'entity',
            url: '/patient-properties/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Patient_properties'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/patient-properties/patient-properties-detail.html',
                    controller: 'Patient_propertiesDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Patient_properties', function($stateParams, Patient_properties) {
                    return Patient_properties.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'patient-properties',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('patient-properties-detail.edit', {
            parent: 'patient-properties-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/patient-properties/patient-properties-dialog.html',
                    controller: 'Patient_propertiesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Patient_properties', function(Patient_properties) {
                            return Patient_properties.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('patient-properties.new', {
            parent: 'patient-properties',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/patient-properties/patient-properties-dialog.html',
                    controller: 'Patient_propertiesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                picture: null,
                                pictureContentType: null,
                                description: null,
                                status: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('patient-properties', null, { reload: 'patient-properties' });
                }, function() {
                    $state.go('patient-properties');
                });
            }]
        })
        .state('patient-properties.edit', {
            parent: 'patient-properties',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/patient-properties/patient-properties-dialog.html',
                    controller: 'Patient_propertiesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Patient_properties', function(Patient_properties) {
                            return Patient_properties.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('patient-properties', null, { reload: 'patient-properties' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('patient-properties.delete', {
            parent: 'patient-properties',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/patient-properties/patient-properties-delete-dialog.html',
                    controller: 'Patient_propertiesDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Patient_properties', function(Patient_properties) {
                            return Patient_properties.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('patient-properties', null, { reload: 'patient-properties' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
