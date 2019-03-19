(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('patient-result-file', {
            parent: 'entity',
            url: '/patient-result-file',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'PatientResultFiles'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/patient-result-file/patient-result-files.html',
                    controller: 'PatientResultFileController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('patient-result-file-detail', {
            parent: 'entity',
            url: '/patient-result-file/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'PatientResultFile'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/patient-result-file/patient-result-file-detail.html',
                    controller: 'PatientResultFileDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'PatientResultFile', function($stateParams, PatientResultFile) {
                    return PatientResultFile.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'patient-result-file',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('patient-result-file-detail.edit', {
            parent: 'patient-result-file-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/patient-result-file/patient-result-file-dialog.html',
                    controller: 'PatientResultFileDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PatientResultFile', function(PatientResultFile) {
                            return PatientResultFile.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('patient-result-file.new', {
            parent: 'patient-result-file',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/patient-result-file/patient-result-file-dialog.html',
                    controller: 'PatientResultFileDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                uuid: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('patient-result-file', null, { reload: 'patient-result-file' });
                }, function() {
                    $state.go('patient-result-file');
                });
            }]
        })
        .state('patient-result-file.edit', {
            parent: 'patient-result-file',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/patient-result-file/patient-result-file-dialog.html',
                    controller: 'PatientResultFileDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PatientResultFile', function(PatientResultFile) {
                            return PatientResultFile.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('patient-result-file', null, { reload: 'patient-result-file' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('patient-result-file.delete', {
            parent: 'patient-result-file',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/patient-result-file/patient-result-file-delete-dialog.html',
                    controller: 'PatientResultFileDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PatientResultFile', function(PatientResultFile) {
                            return PatientResultFile.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('patient-result-file', null, { reload: 'patient-result-file' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
