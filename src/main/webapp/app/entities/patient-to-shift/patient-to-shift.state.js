(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('patient-to-shift', {
            parent: 'entity',
            url: '/patient-to-shift',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'PatientToShifts'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/patient-to-shift/patient-to-shifts.html',
                    controller: 'PatientToShiftController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('patient-to-shift-detail', {
            parent: 'entity',
            url: '/patient-to-shift/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'PatientToShift'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/patient-to-shift/patient-to-shift-detail.html',
                    controller: 'PatientToShiftDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'PatientToShift', function($stateParams, PatientToShift) {
                    return PatientToShift.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'patient-to-shift',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('patient-to-shift-detail.edit', {
            parent: 'patient-to-shift-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/patient-to-shift/patient-to-shift-dialog.html',
                    controller: 'PatientToShiftDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PatientToShift', function(PatientToShift) {
                            return PatientToShift.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('patient-to-shift.new', {
            parent: 'patient-to-shift',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/patient-to-shift/patient-to-shift-dialog.html',
                    controller: 'PatientToShiftDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                status: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('patient-to-shift', null, { reload: 'patient-to-shift' });
                }, function() {
                    $state.go('patient-to-shift');
                });
            }]
        })
        .state('patient-to-shift.edit', {
            parent: 'patient-to-shift',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/patient-to-shift/patient-to-shift-dialog.html',
                    controller: 'PatientToShiftDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PatientToShift', function(PatientToShift) {
                            return PatientToShift.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('patient-to-shift', null, { reload: 'patient-to-shift' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('patient-to-shift.delete', {
            parent: 'patient-to-shift',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/patient-to-shift/patient-to-shift-delete-dialog.html',
                    controller: 'PatientToShiftDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PatientToShift', function(PatientToShift) {
                            return PatientToShift.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('patient-to-shift', null, { reload: 'patient-to-shift' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
