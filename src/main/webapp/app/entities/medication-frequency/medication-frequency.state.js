(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('medication-frequency', {
            parent: 'entity',
            url: '/medication-frequency',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'MedicationFrequencies'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/medication-frequency/medication-frequencies.html',
                    controller: 'MedicationFrequencyController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('medication-frequency-detail', {
            parent: 'entity',
            url: '/medication-frequency/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'MedicationFrequency'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/medication-frequency/medication-frequency-detail.html',
                    controller: 'MedicationFrequencyDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'MedicationFrequency', function($stateParams, MedicationFrequency) {
                    return MedicationFrequency.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'medication-frequency',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('medication-frequency-detail.edit', {
            parent: 'medication-frequency-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/medication-frequency/medication-frequency-dialog.html',
                    controller: 'MedicationFrequencyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['MedicationFrequency', function(MedicationFrequency) {
                            return MedicationFrequency.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('medication-frequency.new', {
            parent: 'medication-frequency',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/medication-frequency/medication-frequency-dialog.html',
                    controller: 'MedicationFrequencyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                hours: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('medication-frequency', null, { reload: 'medication-frequency' });
                }, function() {
                    $state.go('medication-frequency');
                });
            }]
        })
        .state('medication-frequency.edit', {
            parent: 'medication-frequency',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/medication-frequency/medication-frequency-dialog.html',
                    controller: 'MedicationFrequencyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['MedicationFrequency', function(MedicationFrequency) {
                            return MedicationFrequency.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('medication-frequency', null, { reload: 'medication-frequency' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('medication-frequency.delete', {
            parent: 'medication-frequency',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/medication-frequency/medication-frequency-delete-dialog.html',
                    controller: 'MedicationFrequencyDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['MedicationFrequency', function(MedicationFrequency) {
                            return MedicationFrequency.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('medication-frequency', null, { reload: 'medication-frequency' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
