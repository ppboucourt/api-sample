(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('patient-orders.newact', {
                url: '/new-action',
                ncyBreadcrumb: {
                    label: 'New',
                    parent: 'patient-orders'
                },
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'New Action'
                },
                views: {
                    'content@patient-abs': {
                        templateUrl: 'app/entities/patient-action/patient-action-new.html',
                        controller: 'PatientActionNewController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    patientAction: ['$stateParams', function ($stateParams) {
                        return {
                            signatureDate: null,
                            signed: false,
                            id: null,
                            patientActionPres: []
                        };
                    }]
                }
            })
            .state('patient-action-update', {
                parent: 'patient-orders',
                url: '/{oid}/actupdate',
                ncyBreadcrumb: {
                    label: 'Actions Details',
                    parent: 'patient-orders'
                },
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Actions Details'
                },
                views: {
                    'content@patient-abs': {
                        templateUrl: 'app/entities/patient-action/patient-action-update.html',
                        controller: 'PatientActionUpdateController',
                        controllerAs: 'vm'
                    },
                    'action-pre@patient-action-update': {
                        templateUrl: 'app/entities/patient-action-pre/patient-action-pres.html',
                        controller: 'PatientActionPreController',
                        controllerAs: 'vm',
                        resolve: {
                            canCancel: function () {
                                return true;
                            }
                        }
                    }
                },
                resolve: {
                    patientAction: ['$stateParams', 'PatientAction', function ($stateParams, PatientAction) {
                        return PatientAction.get({id: $stateParams.oid}).$promise;
                    }]
                }
            })
        // .state('patient-action', {
        //     parent: 'entity',
        //     url: '/patient-action',
        //     data: {
        //         authorities: ['ROLE_USER'],
        //         pageTitle: 'PatientActions'
        //     },
        //     views: {
        //         'content@': {
        //             templateUrl: 'app/entities/patient-action/patient-actions.html',
        //             controller: 'PatientActionController',
        //             controllerAs: 'vm'
        //         }
        //     },
        //     resolve: {
        //     }
        // })
        // .state('patient-action-detail', {
        //     parent: 'entity',
        //     url: '/patient-action/{id}',
        //     data: {
        //         authorities: ['ROLE_USER'],
        //         pageTitle: 'PatientAction'
        //     },
        //     views: {
        //         'content@': {
        //             templateUrl: 'app/entities/patient-action/patient-action-detail.html',
        //             controller: 'PatientActionDetailController',
        //             controllerAs: 'vm'
        //         }
        //     },
        //     resolve: {
        //         entity: ['$stateParams', 'PatientAction', function($stateParams, PatientAction) {
        //             return PatientAction.get({id : $stateParams.id}).$promise;
        //         }],
        //         previousState: ["$state", function ($state) {
        //             var currentStateData = {
        //                 name: $state.current.name || 'patient-action',
        //                 params: $state.params,
        //                 url: $state.href($state.current.name, $state.params)
        //             };
        //             return currentStateData;
        //         }]
        //     }
        // })
        // .state('patient-action-detail.edit', {
        //     parent: 'patient-action-detail',
        //     url: '/detail/edit',
        //     data: {
        //         authorities: ['ROLE_USER']
        //     },
        //     onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
        //         $uibModal.open({
        //             templateUrl: 'app/entities/patient-action/patient-action-dialog.html',
        //             controller: 'PatientActionDialogController',
        //             controllerAs: 'vm',
        //             backdrop: 'static',
        //             size: 'lg',
        //             resolve: {
        //                 entity: ['PatientAction', function(PatientAction) {
        //                     return PatientAction.get({id : $stateParams.id}).$promise;
        //                 }]
        //             }
        //         }).result.then(function() {
        //             $state.go('^', {}, { reload: false });
        //         }, function() {
        //             $state.go('^');
        //         });
        //     }]
        // })
        // .state('patient-action.new', {
        //     parent: 'patient-action',
        //     url: '/new',
        //     data: {
        //         authorities: ['ROLE_USER']
        //     },
        //     onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
        //         $uibModal.open({
        //             templateUrl: 'app/entities/patient-action/patient-action-dialog.html',
        //             controller: 'PatientActionDialogController',
        //             controllerAs: 'vm',
        //             backdrop: 'static',
        //             size: 'lg',
        //             resolve: {
        //                 entity: function () {
        //                     return {
        //                         signatureDate: null,
        //                         actionStatus: null,
        //                         signed: null,
        //                         asNeeded: null,
        //                         endDate: null,
        //                         id: null
        //                     };
        //                 }
        //             }
        //         }).result.then(function() {
        //             $state.go('patient-action', null, { reload: 'patient-action' });
        //         }, function() {
        //             $state.go('patient-action');
        //         });
        //     }]
        // })
        // .state('patient-action.edit', {
        //     parent: 'patient-action',
        //     url: '/{id}/edit',
        //     data: {
        //         authorities: ['ROLE_USER']
        //     },
        //     onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
        //         $uibModal.open({
        //             templateUrl: 'app/entities/patient-action/patient-action-dialog.html',
        //             controller: 'PatientActionDialogController',
        //             controllerAs: 'vm',
        //             backdrop: 'static',
        //             size: 'lg',
        //             resolve: {
        //                 entity: ['PatientAction', function(PatientAction) {
        //                     return PatientAction.get({id : $stateParams.id}).$promise;
        //                 }]
        //             }
        //         }).result.then(function() {
        //             $state.go('patient-action', null, { reload: 'patient-action' });
        //         }, function() {
        //             $state.go('^');
        //         });
        //     }]
        // })
        // .state('patient-action.delete', {
        //     parent: 'patient-action',
        //     url: '/{id}/delete',
        //     data: {
        //         authorities: ['ROLE_USER']
        //     },
        //     onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
        //         $uibModal.open({
        //             templateUrl: 'app/entities/patient-action/patient-action-delete-dialog.html',
        //             controller: 'PatientActionDeleteController',
        //             controllerAs: 'vm',
        //             size: 'md',
        //             resolve: {
        //                 entity: ['PatientAction', function(PatientAction) {
        //                     return PatientAction.get({id : $stateParams.id}).$promise;
        //                 }]
        //             }
        //         }).result.then(function() {
        //             $state.go('patient-action', null, { reload: 'patient-action' });
        //         }, function() {
        //             $state.go('^');
        //         });
        //     }]
        // });
    }

})();
