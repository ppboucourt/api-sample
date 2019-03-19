(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider', 'ROLES'];

    function stateConfig($stateProvider, ROLES) {
        $stateProvider
        .state('lab-profile', {
            parent: 'entity',
            url: '/lab-profile',
            data: {
                authorities: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE],
                pageTitle: 'LabProfiles'
            },
            ncyBreadcrumb: {
                label: 'Lab Profile',
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/lab-profile/lab-profiles.html',
                    controller: 'LabProfileController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('lab-profile-detail', {
            parent: 'entity',
            url: '/lab-profile/{id}',
            data: {
                authorities: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE],
                pageTitle: 'LabProfile'
            },
            ncyBreadcrumb: {
                label: 'Lab Profile Details',
                parent: 'lab-profile'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/lab-profile/lab-profile-detail.html',
                    controller: 'LabProfileDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'LabProfile', function($stateParams, LabProfile) {
                    return LabProfile.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'lab-profile',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        // .state('lab-profile-detail.edit', {
        //     parent: 'lab-profile-detail',
        //     url: '/detail/edit',
        //     data: {
        //         authorities: [ROLE_USER']
        //     },
        //     onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
        //         $uibModal.open({
        //             templateUrl: 'app/entities/lab-profile/lab-profile-dialog.html',
        //             controller: 'LabProfileDialogController',
        //             controllerAs: 'vm',
        //             backdrop: 'static',
        //             size: 'lg',
        //             resolve: {
        //                 entity: ['LabProfile', function(LabProfile) {
        //                     return LabProfile.get({id : $stateParams.id}).$promise;
        //                 }]
        //             }
        //         }).result.then(function() {
        //             $state.go('^', {}, { reload: false });
        //         }, function() {
        //             $state.go('^');
        //         });
        //     }]
        // })
        .state('lab-profile.new', {
            parent: 'lab-profile',
            url: '/new',
            data: {
                authorities: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE]
            },
            ncyBreadcrumb: {
                label: 'Lab Profile Details',
                parent: 'lab-profile'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/lab-profile/lab-profile-new.html',
                    controller: 'LabProfileNewController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: function () {
                    return {
                        name: null,
                        enabled: false,
                        id: null
                    };
                }
            }
        })
        // .state('lab-profile.edit', {
        //     parent: 'lab-profile',
        //     url: '/{id}/edit',
        //     data: {
        //         authorities: [ROLE_USER']
        //     },
        //     onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
        //         $uibModal.open({
        //             templateUrl: 'app/entities/lab-profile/lab-profile-dialog.html',
        //             controller: 'LabProfileDialogController',
        //             controllerAs: 'vm',
        //             backdrop: 'static',
        //             size: 'lg',
        //             resolve: {
        //                 entity: ['LabProfile', function(LabProfile) {
        //                     return LabProfile.get({id : $stateParams.id}).$promise;
        //                 }]
        //             }
        //         }).result.then(function() {
        //             $state.go('lab-profile', null, { reload: 'lab-profile' });
        //         }, function() {
        //             $state.go('^');
        //         });
        //     }]
        // })
        .state('lab-profile.delete', {
            parent: 'lab-profile',
            url: '/{id}/delete',
            data: {
                authorities: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE]
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lab-profile/lab-profile-delete-dialog.html',
                    controller: 'LabProfileDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['LabProfile', function(LabProfile) {
                            return LabProfile.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('lab-profile', null, { reload: 'lab-profile' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
