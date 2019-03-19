(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('patient-orders-update.patient-order-test-detail', {
                parent: 'patient-orders-update',
                url: '/{tid}/detail',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'PatientOrderTest'
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/patient-order-test/patient-order-test-detail-dialog.html',
                        controller: 'PatientOrderTestDetailDialogController',
                        controllerAs: 'vm',
                        size: 'md',
                        resolve: {
                            patientOrderTest: ['PatientOrderTest', function (PatientOrderTest) {
                                return PatientOrderTest.get({id: $stateParams.tid}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('patient-orders-update', null, {reload: 'patient-orders-update'});
                    }, function () {
                        $state.go('^');
                    });
                }]
            })
            .state('patient-orders-view.patient-order-test-detail', {
                parent: 'patient-orders-update',
                url: '/{tid}/detail',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'PatientOrderTest'
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/patient-order-test/patient-order-test-detail-dialog.html',
                        controller: 'PatientOrderTestDetailDialogController',
                        controllerAs: 'vm',
                        size: 'md',
                        resolve: {
                            patientOrderTest: ['PatientOrder', function (PatientOrder) {
                                return PatientOrder.getPatientOrderTestItems({id: $stateParams.oid}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('patient-order-update', null, {reload: 'patient-order-update'});
                    }, function () {
                        $state.go('^');
                    });
                }]
            })
    }
})();
