(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('SidebarController', SidebarController);

    SidebarController.$inject = ['$rootScope', '$scope', '$state', 'Auth', 'Principal', 'ProfileService', 'LoginService', 'ROLES', 'Facility',
        'CoreService', 'DYMO'];

    function SidebarController($rootScope, $scope, $state, Auth, Principal, ProfileService, LoginService, ROLES, Facility,
                               CoreService, DYMO) {
        $rootScope.background = '';
        DYMO.loadPrinters();
        DYMO.loadDefaultDYMOOrderTemplate();

        var vm = this;

        vm.account = null;

        vm.isNavbarCollapsed = true;
        vm.isControlbarCollapsed = true;
        vm.facilities = [];

        vm.isAuthenticated = Principal.isAuthenticated;

        ProfileService.getProfileInfo().then(function (response) {
            vm.inProduction = response.inProduction;
            vm.swaggerEnabled = response.swaggerEnabled;
        });

        vm.login = login;
        vm.logout = logout;
        vm.toggleNavbar = toggleNavbar;
        vm.collapseNavbar = collapseNavbar;
        vm.$state = $state;

        $rootScope.$on('bluebookApp:updateFacilities', function (event, result) {
            var i = 0;
            for (i = 0; i < vm.facilities.length && vm.facilities[i].id != result.id; i++);
            if (i == vm.facilities.length)
                vm.facilities.push(result);
            else
                vm.facilities[i] = result;
        });

        $rootScope.$on('bluebookApp:deleteFacilities', function (event, result) {
            var i = 0;
            for (i = 0; i < vm.facilities.length && vm.facilities[i].id != result; i++);
            vm.facilities.splice(i, 1);
        });

        vm.sideBarItems = [
            {
                id: 'home',
                open: 'menu-open',
                displayName: 'Dashboard',
                icon: 'fa fa-dashboard',
                state: '',
                style: 'display: block;',
                url: 'home',
                access: [ROLES.ROLE_USER].join(), /*All Access*/
                items: [
                    /*{
                     state: '',
                     displayName: 'Med Pass',
                     url: 'med-pass',
                     icon: 'fa fa-medkit'
                     },

                     {
                     state: '',
                     displayName: 'Physician Review',
                     url: 'unsigned-orders',
                     icon: 'fa fa-user-md'
                     },

                     {
                     state: '',
                     displayName: 'Case Load',
                     url: 'case-load',
                     icon: 'fa fa-briefcase'
                     },
                     {
                     state: '',
                     displayName: 'Current Census',
                     url: 'maintenance',
                     icon: 'fa fa-group'
                     },
                     {
                     state: '',
                     displayName: 'Discharge',
                     url: 'maintenance',
                     icon: 'fa fa-mortar-board'
                     }*/
                ]
            },
            // {
            //     id: 'patient',
            //     state: '',
            //     open: '',
            //     displayName: 'Patient',
            //     icon: 'fa fa-users',
            //     style: 'display: none;',
            //     url: null,
            //     access: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE,
            //         ROLES.ROLE_REGISTER_NURSE, ROLES.ROLE_LICENSE_PRACTITIONER_NURSE, ROLES.ROLE_PRIMARY_THERAPIST, ROLES.ROLE_OTHER_THERAPIST,
            //         ROLES.ROLE_CLINICAL_DIRECTOR, ROLES.ROLE_PHYSICIAN_ASSISTANCE, ROLES.ROLE_MD, ROLES.ROLE_BHT, ROLES.ROLE_LAB].join(),
            //     items: [
            //         {
            //             state: '',
            //             displayName: 'Create',
            //             url: 'patient-create'
            //         },
            //         {
            //             state: '',
            //             displayName: 'Waiting Room',
            //             url: 'waiting-patients',
            //             icon: 'fa fa-hourglass'
            //         },
            //         {
            //             state: '',
            //             displayName: 'Current Patients',
            //             url: 'current-patients',
            //             icon: 'fa fa-users'
            //         },
            //
            //         {
            //             state: '',
            //             displayName: 'Archive',
            //             url: 'archive-patients',
            //             icon: 'fa fa-history'
            //         },
            //
            //     ]
            // },
            {
                id: 'all-patient',
                state: '',
                open: '',
                displayName: 'Patients',
                url: 'current-patients',
                icon: 'fa fa-users',
                style: 'display: none;',
                access: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE,
                    ROLES.ROLE_REGISTER_NURSE, ROLES.ROLE_LICENSE_PRACTITIONER_NURSE, ROLES.ROLE_PRIMARY_THERAPIST,
                    ROLES.ROLE_OTHER_THERAPIST, ROLES.ROLE_BHT].join(),
                items: []
            },
            {
                id: 'occupancy',
                state: '',
                open: '',
                displayName: 'Occupancy',
                url: 'occupancy',
                icon: 'fa fa-bed',
                style: 'display: none;',
                access: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE,
                    ROLES.ROLE_REGISTER_NURSE, ROLES.ROLE_LICENSE_PRACTITIONER_NURSE, ROLES.ROLE_PRIMARY_THERAPIST, ROLES.ROLE_OTHER_THERAPIST,
                    ROLES.ROLE_CLINICAL_DIRECTOR, ROLES.ROLE_PHYSICIAN_ASSISTANCE, ROLES.ROLE_MD, ROLES.ROLE_BHT, ROLES.ROLE_LAB].join(),
                items: []
            },
            {
                id: 'groupSession',
                state: '',
                open: '',
                displayName: 'Group Session',
                icon: 'fa fa-calendar',
                style: 'display: none;',
                url: 'today-group-session',
                access: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE,
                    ROLES.ROLE_REGISTER_NURSE, ROLES.ROLE_LICENSE_PRACTITIONER_NURSE,
                    ROLES.ROLE_PRIMARY_THERAPIST, ROLES.ROLE_OTHER_THERAPIST, ROLES.ROLE_BHT].join(),
                items: [
                    /* {
                     state: '',
                     displayName: 'Todays Group',
                     url: 'today-group-session',
                     icon: 'fa fa-calendar-o'
                     },
                     {
                     state: '',
                     displayName: 'Past Group',
                     url: 'past-group-session',
                     icon: 'fa fa-calendar-times-o'
                     }*/


                ]
            },
            {
                id: 'shifts',
                open: '',
                displayName: 'Shifts',
                icon: 'fa fa-book',
                state: 'shifts',
                style: 'display: none;',
                url: 'shifts',
                access: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE,
                    ROLES.ROLE_REGISTER_NURSE, ROLES.ROLE_LICENSE_PRACTITIONER_NURSE, ROLES.ROLE_BHT].join(),
                items: []
            },
            {
                id: 'fileSystem',
                state: '',
                open: '',
                displayName: 'File System',
                icon: 'fa fa-wrench',
                style: 'display: none;',
                url: null,
                access: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE].join(),
                items: [
                    {
                        state: '',
                        displayName: 'Fax Sends',
                        url: 'fax-send-log',
                        icon: 'fa fa-fax'
                    },

                ]
            },
            {
                id: 'contacts',
                open: '',
                displayName: 'Contacts',
                icon: 'fa fa-phone',
                state: 'contacts-facility',
                style: 'display: none;',
                url: 'contacts-facility',
                access: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE].join(),
                items: []
            },
            // {
            //     id: 'billing',
            //     state: '',
            //     open: '',
            //     displayName: 'Billing',
            //     url: 'maintenance',
            //     icon: 'fa fa-money',
            //     style: 'display: none;',
            //     access: [ROLES.ROLE_USER].join(),
            //     items: []
            // },
            {
                id: 'labRequisitions',
                state: '',
                open: '',
                displayName: 'Lab Requisitions',
                icon: 'fa fa-flask',
                style: 'display: none;',
                url: 'lab-requisitions',
                access: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE,
                    ROLES.ROLE_REGISTER_NURSE, ROLES.ROLE_LICENSE_PRACTITIONER_NURSE, ROLES.ROLE_MD,
                    ROLES.ROLE_CLINICAL_DIRECTOR, ROLES.ROLE_PHYSICIAN_ASSISTANCE, ROLES.ROLE_LAB, ROLES.ROLE_BHT].join(),
                items: []
            },
            {
                id: 'labResults',
                state: '',
                open: '',
                displayName: 'Lab Results',
                url: 'lab-results',
                icon: 'fa fa-handshake-o',
                style: 'display: none;',
                access: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE,
                    ROLES.ROLE_REGISTER_NURSE, ROLES.ROLE_LICENSE_PRACTITIONER_NURSE, ROLES.ROLE_MD,
                    ROLES.ROLE_CLINICAL_DIRECTOR, ROLES.ROLE_PHYSICIAN_ASSISTANCE, ROLES.ROLE_LAB, ROLES.ROLE_BHT].join(),
                items: []
            },
            // {
            //     id: 'reports',
            //     state: '',
            //     open: '',
            //     displayName: 'Reports',
            //     url: 'maintenance',
            //     icon: 'fa fa-pie-chart',
            //     style: 'display: none;',
            //     access: [ROLES.ROLE_USER].join(),
            //     items: []
            // },
            {
                id: 'templates',
                state: '',
                open: '',
                displayName: 'Templates',
                icon: 'fa fa-file-pdf-o',
                style: 'display: none;',
                url: null,
                access: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE].join(),
                items: [
                    {
                        state: '',
                        displayName: 'Consent Forms',
                        url: 'forms',
                        icon: 'fa fa-calendar-o'
                    },
                    {
                        state: '',
                        displayName: 'Evaluations',
                        url: 'evaluation-template',
                        icon: 'fa fa-stethoscope'
                    },

                    // {
                    //     state: '',
                    //     displayName: 'Orders',
                    //     url: 'all-orders',
                    //     icon: 'fa fa-cart-plus',
                    // },

                    // {
                    //     state: '',
                    //     displayName: 'Lab Profiles',
                    //     url: 'lab-profile',
                    //     icon: 'fa fa-clipboard'
                    // },

                    {
                        state: '',
                        displayName: 'Group Sessions',
                        url: 'group-session',
                        icon: 'fa fa-users'
                    }

                ]
            },
            {
                id: 'config',
                state: '',
                open: '',
                displayName: 'Configuration',
                url: null,
                icon: 'fa fa-cog',
                style: 'display: none;',
                access: [ROLES.ROLE_ADMIN, ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE].join(),
                items: [
                    {
                        state: '',
                        displayName: 'Corporation',
                        url: 'corporation',
                        icon: 'fa fa-building'
                    },
                    {
                        state: '',
                        displayName: 'Facility',
                        url: 'facility',
                        icon: 'fa fa-h-square'
                    },
                    {
                        state: '',
                        displayName: 'Building',
                        url: 'building',
                        icon: 'fa fa-hospital-o'
                    },
                    {
                        state: '',
                        displayName: 'Room',
                        url: 'room',
                        icon: 'fa fa-rocket'
                    },
                    {
                        state: '',
                        displayName: 'Bed',
                        url: 'bed',
                        icon: 'fa fa-rocket'
                    },
                    {
                        state: '',
                        displayName: 'Employee',
                        url: 'employee',
                        icon: ''
                    },
                    {
                        state: '',
                        displayName: 'Patient Process',
                        url: 'type-patient-process',
                        icon: ''
                    },
                    {
                        state: '',
                        displayName: 'Service Provider',
                        url: 'service-provider',
                        icon: ''
                    },
                    {
                        state: '',
                        displayName: 'Medications',
                        url: 'medications',
                        icon: ''
                    }
                ]
            }
            /*,
            {
                id: 'patientDetailsN',
                state: '',
                open: '',
                displayName: 'Patient Details New',
                icon: 'fa fa-user-circle-o',
                style: 'display: none;',
                url: 'patient-details-n',
                access: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE,
                    ROLES.ROLE_REGISTER_NURSE, ROLES.ROLE_LICENSE_PRACTITIONER_NURSE, ROLES.ROLE_MD,
                    ROLES.ROLE_CLINICAL_DIRECTOR, ROLES.ROLE_PHYSICIAN_ASSISTANCE, ROLES.ROLE_LAB, ROLES.ROLE_BHT].join(),
                items: []
            }
            */
        ];

        $rootScope.$on('$stateChangeSuccess',
            function (event, toState, toParams, fromState, fromParams) {
                selectMenuItem($state.current.name);
            });

        selectMenuItem($state.current.name);

        function selectMenuItem(url) {
            var lessChild = null;
            var parent = null;
            var child = null;
            for (var i = 0; i < vm.sideBarItems.length; i++) {
                for (var j = 0; j < vm.sideBarItems[i].items.length; j++) {
                    if (vm.sideBarItems[i].items[j].items == null) {
                        if (vm.sideBarItems[i].items[j].url == url) {
                            parent = i;
                            child = j;
                        } else {
                            vm.sideBarItems[i].open = '';
                            vm.sideBarItems[i].style = 'display: none;';
                            vm.sideBarItems[i].state = '';
                            vm.sideBarItems[i].items[j].state = '';
                            vm.sideBarItems[i].items[j].icon = "fa fa-circle-o";
                            vm.sideBarItems[i].items[j].active = '';
                        }
                    } else {
                        for (var k = 0; k < vm.sideBarItems[i].items[j].items.length; k++) {
                            if (vm.sideBarItems[i].items[j].items[k].url == url) {
                                parent = i;
                                child = j;
                                lessChild = k;
                            } else {
                                vm.sideBarItems[i].open = '';
                                vm.sideBarItems[i].style = 'display: none;';
                                vm.sideBarItems[i].state = '';
                                vm.sideBarItems[i].items[j].open = '';
                                vm.sideBarItems[i].items[j].style = 'display: none;';
                                vm.sideBarItems[i].items[j].state = '';
                                vm.sideBarItems[i].items[j].items[k].state = '';
                                vm.sideBarItems[i].items[j].items[k].icon = "fa fa-circle-o";
                                vm.sideBarItems[i].items[j].items[k].active = '';
                            }
                        }
                    }
                }
            }

            if (parent != null && child != null) {
                if (lessChild == null) {
                    vm.sideBarItems[parent].open = 'menu-open';
                    vm.sideBarItems[parent].style = 'display: block;';
                    vm.sideBarItems[parent].state = 'active';

                    vm.sideBarItems[parent].items[child].state = 'active';
                    vm.sideBarItems[parent].items[child].icon = "fa fa-dot-circle-o";
                    vm.sideBarItems[parent].items[child].active = 'active';
                } else {
                    vm.sideBarItems[parent].open = 'menu-open';
                    vm.sideBarItems[parent].style = 'display: block;';
                    vm.sideBarItems[parent].state = 'active';

                    vm.sideBarItems[parent].items[child].open = 'menu-open';
                    vm.sideBarItems[parent].items[child].style = "display: block;";
                    vm.sideBarItems[parent].items[child].state = 'active';

                    vm.sideBarItems[parent].items[child].items[lessChild].state = 'active';
                    vm.sideBarItems[parent].items[child].items[lessChild].icon = "fa fa-dot-circle-o";
                    vm.sideBarItems[parent].items[child].items[lessChild].active = 'active';
                }
            }
        }

        $scope.$on('authenticationSuccess', function () {
            getAccount();
        });

        getAccount();

        function getAccount() {
            Principal.identity().then(function (account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }

        function login() {
            collapseNavbar();
            LoginService.open();
        }

        function logout() {
            collapseNavbar();
            Auth.logout();
            $state.go('home');
        }

        function toggleNavbar() {
            console.log('*********************88');
            vm.isNavbarCollapsed = !vm.isNavbarCollapsed;
        }

        function collapseNavbar() {
            console.log('--------------------------------------------------------------');
            vm.isNavbarCollapsed = true;
        }

        function toggleControlbar() {
            vm.isControlbarCollapsed = !vm.isControlbarCollapsed;
        }

        loadFacilities();

        function loadFacilities() {
            Facility.query(function (result) {

                if (result.length > 0) {
                    vm.facilities = result;
                    vm.currentFacility = CoreService.getCurrentFacility();

                    if (!vm.currentFacility) {
                        vm.currentFacility = result[0];
                        CoreService.setCurrentFacility(result[0]);
                    }
                    //Emit an event
                    $scope.$emit('bluebookApp:setCurrentFacility', vm.currentFacility);
                }
            });
        }

        $scope.chageFacility = function (facility) {
            if (facility) {
                CoreService.setCurrentFacility(facility);
                //Emit an event
                $scope.$emit('bluebookApp:setCurrentFacility', facility);
                $state.go('home');
            }
        }
    }
})();
