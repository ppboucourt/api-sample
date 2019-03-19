(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        /*.state('hospitalization', {
            parent: 'entity',
            url: '/hospitalization',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Hospitalizations'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/hospitalization/hospitalizations.html',
                    controller: 'HospitalizationController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('hospitalization-detail', {
            parent: 'entity',
            url: '/hospitalization/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Hospitalization'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/hospitalization/hospitalization-detail.html',
                    controller: 'HospitalizationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Hospitalization', function($stateParams, Hospitalization) {
                    return Hospitalization.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'hospitalization',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('hospitalization-detail.edit', {
            parent: 'hospitalization-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/hospitalization/hospitalization-dialog.html',
                    controller: 'HospitalizationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Hospitalization', function(Hospitalization) {
                            return Hospitalization.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('hospitalization.new', {
            parent: 'hospitalization',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/hospitalization/hospitalization-dialog.html',
                    controller: 'HospitalizationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                admissionDiagnosis: null,
                                admissionDate: null,
                                hospital: null,
                                admittingPhysician: null,
                                dischargeDate: null,
                                dischargeTo: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('hospitalization', null, { reload: 'hospitalization' });
                }, function() {
                    $state.go('hospitalization');
                });
            }]
        })
        .state('hospitalization.edit', {
            parent: 'hospitalization',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/hospitalization/hospitalization-dialog.html',
                    controller: 'HospitalizationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Hospitalization', function(Hospitalization) {
                            return Hospitalization.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('hospitalization', null, { reload: 'hospitalization' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('hospitalization.delete', {
            parent: 'hospitalization',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/hospitalization/hospitalization-delete-dialog.html',
                    controller: 'HospitalizationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Hospitalization', function(Hospitalization) {
                            return Hospitalization.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('hospitalization', null, { reload: 'hospitalization' });
                }, function() {
                    $state.go('^');
                });
            }]
        });*/

    .state('hospitalization-detail', {
            parent: 'entity',
            url: '/hospitalization/{hid}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Hospitalization'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/hospitalization/hospitalization-detail.html',
                    controller: 'HospitalizationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Hospitalization', function ($stateParams, Hospitalization) {
                    return Hospitalization.get({id: $stateParams.hid}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'hospitalization',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
            .state('hospitalization-new', {
                parent: 'patient',
                url: '/hnew',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', 'chart', function ($stateParams, $state, $uibModal, chart) {
                    $uibModal.open({
                        templateUrl: 'app/entities/hospitalization/hospitalization-dialog.html',
                        controller: 'HospitalizationDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            hospitalization: function () {
                                return {
                                    admissionDiagnosis: null,
                                    admissionDate: null,
                                    hospital: null,
                                    admittingPhysician: null,
                                    dischargeDate: null,
                                    dischargeTo: null,
                                    id: null
                                };
                            },
                            chart: chart
                        }
                    }).result.then(function () {
                        $state.go('patient', null, {reload: 'patient'});
                    }, function () {
                        $state.go('patient');
                    });
                }]
            })
            .state('hospitalization-edit', {
                parent: 'patient',
                url: '/{hid}/hedit',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', 'chart', function ($stateParams, $state, $uibModal, chart) {
                    $uibModal.open({
                        templateUrl: 'app/entities/hospitalization/hospitalization-dialog.html',
                        controller: 'HospitalizationDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            hospitalization: ['Hospitalization', function (Hospitalization) {
                                return Hospitalization.get({id: $stateParams.hid}).$promise;
                            }],
                            chart: chart
                        }
                    }).result.then(function () {
                        $state.go('patient', null, {reload: 'patient'});
                    }, function () {
                        $state.go('patient');
                    });
                }]
            })
            .state('hospitalization-delete', {
                parent: 'patient',
                url: '/{hid}/hdelete',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/hospitalization/hospitalization-delete-dialog.html',
                        controller: 'HospitalizationDeleteController',
                        controllerAs: 'vm',
                        size: 'md',
                        resolve: {
                            entity: ['Hospitalization', function (Hospitalization) {
                                return Hospitalization.get({id: $stateParams.hid}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('patient', null, {reload: 'patient'});
                    }, function () {
                        $state.go('patient');
                    });
                }]
            });
    }
})();
