(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider', 'ROLES'];

    function stateConfig($stateProvider, ROLES) {
        $stateProvider
        .state('concurrent-reviews', {
            parent: 'entity',
            url: '/concurrent-reviews',
            data: {
                authorities: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE, ROLES.ROLE_REGISTER_NURSE, ROLES.ROLE_LICENSE_PRACTITIONER_NURSE, ROLES.ROLE_PRIMARY_THERAPIST, ROLES.ROLE_OTHER_THERAPIST, ROLES.ROLE_CLINICAL_DIRECTOR, ROLES.ROLE_PHYSICIAN_ASSISTANCE, ROLES.ROLE_MD, ROLES.ROLE_BHT, ROLES.ROLE_LAB],
                pageTitle: 'ConcurrentReviews'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/concurrent-reviews/concurrent-reviews.html',
                    controller: 'ConcurrentReviewsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                // chart: ['$stateParams', 'Chart', 'CoreService', function($stateParams, Chart) {
                //     return Chart.get({id : $stateParams.id}).$promise;
                // }]
            }
        })

        .state('concurrent-reviews-edit', {
            parent: 'patient',
            url: '/edit/{crid}',
            data: {
                authorities: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE, ROLES.ROLE_REGISTER_NURSE, ROLES.ROLE_LICENSE_PRACTITIONER_NURSE, ROLES.ROLE_PRIMARY_THERAPIST, ROLES.ROLE_OTHER_THERAPIST, ROLES.ROLE_CLINICAL_DIRECTOR, ROLES.ROLE_PHYSICIAN_ASSISTANCE, ROLES.ROLE_MD, ROLES.ROLE_BHT, ROLES.ROLE_LAB]
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/concurrent-reviews/concurrent-reviews-dialog.html',
                    controller: 'ConcurrentReviewsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ConcurrentReviews', function(ConcurrentReviews) {
                            return ConcurrentReviews.get({id : $stateParams.crid}).$promise;
                        }],
                        chart: ['$stateParams', 'Chart', function($stateParams, Chart) {
                            return Chart.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('concurrent-reviews-new', {
            parent: 'patient',
            url: '/new-concurrent',
            data: {
                authorities: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE, ROLES.ROLE_REGISTER_NURSE, ROLES.ROLE_LICENSE_PRACTITIONER_NURSE, ROLES.ROLE_PRIMARY_THERAPIST, ROLES.ROLE_OTHER_THERAPIST, ROLES.ROLE_CLINICAL_DIRECTOR, ROLES.ROLE_PHYSICIAN_ASSISTANCE, ROLES.ROLE_MD, ROLES.ROLE_BHT, ROLES.ROLE_LAB]
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/concurrent-reviews/concurrent-reviews-dialog.html',
                    controller: 'ConcurrentReviewsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                       entity: function () {
                            return {
                                authorizationDate: null,
                                numberDates: null,
                                frequency: null,
                                typeLevelCare: null,
                                startDate: null,
                                endDate: null,
                                lastCoverageDate: null,
                                authorizationNumber: null,
                                nextReviewDate: null,
                                insuranceCompany: null,
                                notes: null,
                                status: null,
                                id: null
                            };


                        },
                        chart: ['$stateParams', 'Chart', function($stateParams, Chart) {
                            return Chart.get({id : $stateParams.id}).$promise;
                        }],
                    }
                }).result.then(function() {
                    $state.go('patient', null, { reload: 'patient' });
                }, function() {
                    $state.go('patient');
                });
            }]
        })
        .state('concurrent-reviews.delete', {
            parent: 'patient',
            url: '/delete/{crid}',
            data: {
                authorities: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE, ROLES.ROLE_REGISTER_NURSE, ROLES.ROLE_LICENSE_PRACTITIONER_NURSE, ROLES.ROLE_PRIMARY_THERAPIST, ROLES.ROLE_OTHER_THERAPIST, ROLES.ROLE_CLINICAL_DIRECTOR, ROLES.ROLE_PHYSICIAN_ASSISTANCE, ROLES.ROLE_MD, ROLES.ROLE_BHT, ROLES.ROLE_LAB]
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/concurrent-reviews/concurrent-reviews-delete-dialog.html',
                    controller: 'ConcurrentReviewsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ConcurrentReviews', function(ConcurrentReviews) {
                            return ConcurrentReviews.get({id : $stateParams.crid}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('patient', null, { reload: 'patient' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
