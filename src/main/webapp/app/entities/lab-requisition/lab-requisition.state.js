(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider', 'ROLES'];

    function stateConfig($stateProvider, ROLES) {
        $stateProvider
        .state('lab-requisition', {
            parent: 'entity',
            url: '/lab-requisition',
            data: {
                authorities: [
                    ROLES.ROLE_SUPER_ADMIN,
                    ROLES.ROLE_PROGRAM_DIRECTOR,
                    ROLES.ROLE_CASE_MANAGER,
                    ROLES.ROLE_DIRECTOR_NURSE,
                    ROLES.ROLE_REGISTER_NURSE,
                    ROLES.ROLE_LICENSE_PRACTITIONER_NURSE,
                    ROLES.ROLE_MD,
                    ROLES.ROLE_CLINICAL_DIRECTOR,
                    ROLES.ROLE_PHYSICIAN_ASSISTANCE,
                    ROLES.ROLE_LAB,
                    ROLES.ROLE_BHT
                ],
                pageTitle: 'LabRequisitions'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/lab-requisition/lab-requisitions.html',
                    controller: 'LabRequisitionController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('lab-requisition-detail', {
            parent: 'entity',
            url: '/lab-requisition/{id}',
            data: {
                authorities: [
                    ROLES.ROLE_SUPER_ADMIN,
                    ROLES.ROLE_PROGRAM_DIRECTOR,
                    ROLES.ROLE_CASE_MANAGER,
                    ROLES.ROLE_DIRECTOR_NURSE,
                    ROLES.ROLE_REGISTER_NURSE,
                    ROLES.ROLE_LICENSE_PRACTITIONER_NURSE,
                    ROLES.ROLE_MD,
                    ROLES.ROLE_CLINICAL_DIRECTOR,
                    ROLES.ROLE_PHYSICIAN_ASSISTANCE,
                    ROLES.ROLE_LAB,
                    ROLES.ROLE_BHT
                ],
                pageTitle: 'LabRequisition'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/lab-requisition/lab-requisition-detail.html',
                    controller: 'LabRequisitionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'LabRequisition', function($stateParams, LabRequisition) {
                    return LabRequisition.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'lab-requisition',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('lab-requisition-detail.edit', {
            parent: 'lab-requisition-detail',
            url: '/detail/edit',
            data: {
                authorities: [
                    ROLES.ROLE_SUPER_ADMIN,
                    ROLES.ROLE_PROGRAM_DIRECTOR,
                    ROLES.ROLE_CASE_MANAGER,
                    ROLES.ROLE_DIRECTOR_NURSE,
                    ROLES.ROLE_REGISTER_NURSE,
                    ROLES.ROLE_LICENSE_PRACTITIONER_NURSE,
                    ROLES.ROLE_MD,
                    ROLES.ROLE_CLINICAL_DIRECTOR,
                    ROLES.ROLE_PHYSICIAN_ASSISTANCE,
                    ROLES.ROLE_LAB,
                    ROLES.ROLE_BHT
                ]
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lab-requisition/lab-requisition-dialog.html',
                    controller: 'LabRequisitionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LabRequisition', function(LabRequisition) {
                            return LabRequisition.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('lab-requisition.new', {
            parent: 'lab-requisition',
            url: '/new',
            data: {
                authorities: [
                    ROLES.ROLE_SUPER_ADMIN,
                    ROLES.ROLE_PROGRAM_DIRECTOR,
                    ROLES.ROLE_CASE_MANAGER,
                    ROLES.ROLE_DIRECTOR_NURSE,
                    ROLES.ROLE_REGISTER_NURSE,
                    ROLES.ROLE_LICENSE_PRACTITIONER_NURSE,
                    ROLES.ROLE_MD,
                    ROLES.ROLE_CLINICAL_DIRECTOR,
                    ROLES.ROLE_PHYSICIAN_ASSISTANCE,
                    ROLES.ROLE_LAB,
                    ROLES.ROLE_BHT
                ]
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lab-requisition/lab-requisition-dialog.html',
                    controller: 'LabRequisitionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                patient_signature: null,
                                status: null,
                                barcode: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('lab-requisition', null, { reload: 'lab-requisition' });
                }, function() {
                    $state.go('lab-requisition');
                });
            }]
        })
        .state('lab-requisition.edit', {
            parent: 'lab-requisition',
            url: '/{id}/edit',
            data: {
                authorities: [
                    ROLES.ROLE_SUPER_ADMIN,
                    ROLES.ROLE_PROGRAM_DIRECTOR,
                    ROLES.ROLE_CASE_MANAGER,
                    ROLES.ROLE_DIRECTOR_NURSE,
                    ROLES.ROLE_REGISTER_NURSE,
                    ROLES.ROLE_LICENSE_PRACTITIONER_NURSE,
                    ROLES.ROLE_MD,
                    ROLES.ROLE_CLINICAL_DIRECTOR,
                    ROLES.ROLE_PHYSICIAN_ASSISTANCE,
                    ROLES.ROLE_LAB,
                    ROLES.ROLE_BHT
                ]
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lab-requisition/lab-requisition-dialog.html',
                    controller: 'LabRequisitionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LabRequisition', function(LabRequisition) {
                            return LabRequisition.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('lab-requisition', null, { reload: 'lab-requisition' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('lab-requisition.delete', {
            parent: 'lab-requisition',
            url: '/{id}/delete',
            data: {
                authorities: [
                    ROLES.ROLE_SUPER_ADMIN,
                    ROLES.ROLE_PROGRAM_DIRECTOR,
                    ROLES.ROLE_CASE_MANAGER,
                    ROLES.ROLE_DIRECTOR_NURSE,
                    ROLES.ROLE_REGISTER_NURSE,
                    ROLES.ROLE_LICENSE_PRACTITIONER_NURSE,
                    ROLES.ROLE_MD,
                    ROLES.ROLE_CLINICAL_DIRECTOR,
                    ROLES.ROLE_PHYSICIAN_ASSISTANCE,
                    ROLES.ROLE_LAB,
                    ROLES.ROLE_BHT
                ]
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lab-requisition/lab-requisition-delete-dialog.html',
                    controller: 'LabRequisitionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['LabRequisition', function(LabRequisition) {
                            return LabRequisition.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('lab-requisition', null, { reload: 'lab-requisition' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
