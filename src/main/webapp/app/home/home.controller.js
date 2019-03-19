(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state', 'Bed', 'CoreService', 'Chart', 'ChartToMedications', 'DTColumnBuilder', 'DTOptionsBuilder', '$q',
        'GroupSession', 'moment', '$rootScope', 'Corporation', 'Dashboard', 'toastr', '$timeout'];

    function HomeController($scope, Principal, LoginService, $state, Bed, CoreService, Chart, ChartToMedications, DTColumnBuilder, DTOptionsBuilder, $q,
                            GroupSession, moment, $rootScope, Corporation, Dashboard, toastr, $timeout) {
        var vm = this;
        vm.$timeout = $timeout;
        vm.toastr = toastr;
        vm.facility = CoreService.getCurrentFacility();

        CoreService.validateRemember();

        vm.dashboardReport = {};
        vm.dashboardReport.unsignedOrdersByDoctor = 0;
        vm.dashboardReport.unsignedOrders = 0;
        vm.dashboardReport.unasignedLabResults = 0;
        vm.dashboardReport.mosthlyCountOfChart = 0;
        vm.dashboardReport.incomingChartForThe3NextDays = [];
        vm.dashboardReport.dischargedForThe3NextDays = [];
        vm.dashboardReport.inProgressGroupSessionDetails = 0;
        vm.dashboardReport.inProgressGroupSessionDetailsPerCent = 0;
        vm.dashboardReport.pendingReviewGroupSessionDetails = 0;
        vm.dashboardReport.inProgressGroupSessionDetailsPerCent = 0;
        vm.dashboardReport.completedGroupSessionDetails = 0;
        vm.dashboardReport.totalGroupsForTheDay = 0;


        //functions
        vm.register = register;

        //Variable
        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.freeBeds = [];
        vm.active = [];
        vm.waiting = [];
        vm.Messages = [];
        vm.prescriptionTimes = [];
        vm.searchFilter = [];
        vm.groupSessions = [];
        vm.chartMedDistinct = [];
        vm.chartMedicationsToSave = {};
        vm.dtInstance = {};
        vm.loadAllChart = loadAllChart;
        vm.groupSessionComingUp = [];
        vm.groupSessionInProgress = [];
        vm.groupSessionFinalized = [];
        vm.sum = [];
        vm.currentPatient = [];
        vm.bashboardData = {
            bedAvailability: {female: 0, male: 0},
            concurrentReviews: {}
        };
        vm.testList = [
            {name: 'Pedro Enrique', age: 28, address: '333 N Boynton Beach Blvd Boynton Beach', mrNo: 2017 - 21},
            {name: 'Manuel Morejon', age: 24, address: '1298 W Deerfield Beach Hilssboro', mrNo: 2017 - 25},
            {name: 'Pedro Garcia', age: 32, address: '333 N Boynton Beach Blvd Boynton Beach', mrNo: 2017 - 26},
            {name: 'Garrido Enrique', age: 33, address: '333 N Boynton Beach Blvd Boynton Beach', mrNo: 2017 - 29},
            {name: 'Mike Herms', age: 38, address: '333 N Boynton Beach Blvd Boynton Beach', mrNo: 2017 - 30},
            {name: 'Heisemberg Jhon', age: 25, address: '333 N Boynton Beach Blvd Boynton Beach', mrNo: 2017 - 33},
            {name: 'Manuel Douglas', age: 22, address: '333 N Boynton Beach Blvd Boynton Beach', mrNo: 2017 - 35},
            {name: 'Henrry Rive', age: 27, address: '333 N Boynton Beach Blvd Boynton Beach', mrNo: 2017 - 36},
            {name: 'Hector Gonzales', age: 30, address: '333 N Boynton Beach Blvd Boynton Beach', mrNo: 2017 - 38},
            {name: 'Gabriel Batista', age: 31, address: '333 N Boynton Beach Blvd Boynton Beach', mrNo: 2017 - 39},
            {name: 'Bryan Rodriguez', age: 36, address: '333 N Boynton Beach Blvd Boynton Beach', mrNo: 2017 - 42}
        ];
        $scope.$on('authenticationSuccess', function () {
            getAccount();
        });

        // Dashboard Pie Charts configuration & data

        // Beds Availability
        vm.optionsBeds = {
            chart: {
                type: 'pieChart',
                showLegend: false,
                margin: {"top": 0, "right": 0, "bottom": 0, "left": 0},
                color: ["#f39c12", "#00c0ef", "#00a65a", "#dd4b39"],
                height: 200,
                width: 200,
                x: function (d) {
                    return d.key;
                },
                y: function (d) {
                    return d.y;
                },
                duration: 500,
                showLabels: true,
                labelType: 'value',
                labelThreshold: 0.01,
                labelSunbeamLayout: false,
                valueFormat: d3.format(',.0d')
            }
        };

        /**
         * Get all dashboard data
         */
        function getAllDashboardData() {
            Dashboard.getAllDashboardData({fId: vm.facility.id}).$promise
                .then(function (data) {
                    vm.bashboardData = data;
                    //Pie BEd Availability
                    vm.pieChartDataBeds = [{
                        key: 'Female',
                        y: data.bedAvailability.female
                    }, {
                        key: 'Male',
                        y: data.bedAvailability.male
                    }];

                    //Pie level of care
                    vm.pieChartDataLevelOfCares = _.chain(data.typeLevelCare)
                        .map(function (value, index) {
                            return {
                                key: index,
                                y: value
                            }
                        })
                        .filter(function (val) {
                            return val.y > 0;
                        })
                        .value();

                    //Pie payment methods
                    vm.pieChartDataPaymentMethods = _.chain(data.typePaymentMethod)
                        .map(function (value, index) {
                            return {
                                key: index,
                                y: value
                            }
                        })
                        .filter(function (val) {
                            return val.y > 0;
                        })
                        .value();

                    //Pie missing authorization
                    vm.pieChartDataPatientsMissingAuth = [{
                        key: 'Missing',
                        y: data.concurrentReviews.no_concurrent_review
                    }, {
                        key: 'Authorized',
                        y: data.concurrentReviews.concurrent_review
                    }];
                })
        }


        vm.pieChartDataBeds = [];

        //Patients by level of cares
        vm.optionsLevelOfCares = {
            chart: {
                type: 'pieChart',
                showLegend: false,
                margin: {"top": 0, "right": 0, "bottom": 0, "left": 0},
                color: ["#00a65a", "#dd4b39", "#f39c12", "#00c0ef", '#fbf00f'],
                height: 200,
                width: 200,
                x: function (d) {
                    return d.key;
                },
                y: function (d) {
                    return d.y;
                },
                duration: 500,
                showLabels: true,
                labelType: 'value',
                labelThreshold: 0.01,
                labelSunbeamLayout: false,
                valueFormat: d3.format(',.0d')
            }
        };

        vm.pieChartDataLevelOfCares = [];

        //Patients by Payment Methods
        vm.optionsPaymentMethods = {
            chart: {
                type: 'pieChart',
                showLegend: false,
                margin: {"top": 0, "right": 0, "bottom": 0, "left": 0},
                color: ["#00a65a", "#dd4b39", "#00c0ef", "#fbf00f"],
                height: 200,
                width: 200,
                x: function (d) {
                    return d.key;
                },
                y: function (d) {
                    return d.y;
                },
                duration: 500,
                showLabels: true,
                labelType: 'value',
                labelThreshold: 0.01,
                labelSunbeamLayout: false,
                valueFormat: d3.format(',.0d')
            }
        };
        vm.pieChartDataPaymentMethods = [];

        //Patients missing authorization
        vm.optionsPatientsMissingAuth = {
            chart: {
                type: 'pieChart',
                showLegend: false,
                margin: {"top": 0, "right": 0, "bottom": 0, "left": 0},
                color: ["#dd4b39", "#00A65A"],
                height: 200,
                width: 200,
                x: function (d) {
                    return d.key;
                },
                y: function (d) {
                    return d.y;
                },
                duration: 500,
                showLabels: true,
                labelType: 'value',
                labelThreshold: 0.01,
                labelSunbeamLayout: false,
                valueFormat: d3.format(',.0d')
            }
        };
        vm.pieChartDataPatientsMissingAuth = [];

        //Listener when the facility was selected
        $rootScope.$on('bluebookApp:setCurrentFacility', function (event, result) {
            vm.facility = result;
            // getDashboardReport();
            loadAllReportTuning();
            loadAll();
        });

        var facility = CoreService.getCurrentFacility();
        if (facility) {
            loadAllReportTuning();
            loadAll();
        }
        // getAccount();

        function loadAll() {
            if (vm.facility) {
                bedsAll();
                active();
                waiting();
                getAllDashboardData();
                // console.log("vm.facility", vm.facility);
            }
            if (!CoreService.getCorporation()) {
                Corporation.get({id: 1}, function (result) {
                    CoreService.setCorporation(result);
                });
            }
        }

        function getAccount() {
            Principal.identity().then(function (account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }

        function register() {
            $state.go('register');
        }

        function waiting() {
            Chart.waitingRoom({id: vm.facility.id}, function (result) {
                vm.waitingRoom = result;
                vm.labels = ["Current Patients", "Waiting Room"];
                vm.data = [vm.currentPatient.length, vm.waitingRoom.length]
                // vm.chartData = [
                //     {label: "Current Patients", value: vm.currentPatient.length},
                //     {label: "Waiting Room",value: vm.waitingRoom.length}
                // ];
            });
        }

        function active() {
            Chart.currentPatient({id: vm.facility.id}, function (result) {
                vm.currentPatient = result;

            });
        }

        function bedsAll() {
            Bed.freeBeds({id: vm.facility.id, actualChart: 0}, function (result) {
                vm.freeBeds = result;

            });
        }

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function () {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                ChartToMedications.medicationsChartByFacility({id: vm.facility.id}, function (result) {
                    vm.chartMedications = result;
                    defer.resolve(result);
                    prepareData(result);
                });
            } else {
                var searchCriteria = {prescriptionTime: vm.searchQuery.prescriptionTime};
                defer.resolve($filter('filter')(vm.chartMedications, searchCriteria, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip').withOption('fnRowCallback',
            function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
                $compile(nRow)($scope);
            });

        vm.dtColumns = [
            DTColumnBuilder.newColumn(null).withTitle('Times').renderWith(function (data, type, full) {
                return data.prescriptionTime;
            }),
            DTColumnBuilder.newColumn(null).withTitle('Patient').renderWith(function (data, type, full) {
                return data.chart.patient ? data.chart.patient.fullNameCapital : "Empty";
            }),

            DTColumnBuilder.newColumn('medication').withTitle('Medications').renderWith(function (data, type, full) {
                return data.medication;
            }),
            DTColumnBuilder.newColumn(null).withTitle('Taken').withOption('with', '45px').notSortable()
                .renderWith(takenHtml)
            ,

        ];

        function loadAllChart() {
            ChartToMedications.prescriptionDistinct({id: vm.facility.id}, function (result) {
                var time;
                for (var i = 0; i < result.length; i++) {
                    time = {};
                    time.id = i;
                    time.prescriptionTime = moment(result[i]).format('hh:mm a');
                    time.prescriptionDate = moment(result[i]).format('YYYY-MM-DD');
                    vm.chartMedDistinct.push(time);
                }
            });
        }

        function actionsHtml(data) {
            return '<a class="btn-sm btn-primary" ' +
                // 'ui-sref="contacts-facility.edit({id:' + data.id + '})" href="#/contacts-facility/' + data.id + '/edit"'+
                '>' +
                '<i class="fa fa-eye"></i></a>';
        }

        function takenHtml(data) {
            return '&nbsp;&nbsp;&nbsp;&nbsp;<span class="label label-success" ng-click="vm.changeTakenYes(' + data.id + ')" ' +
                'style="cursor: pointer;">Yes</span></td>&nbsp;&nbsp;&nbsp;&nbsp;' +
                '<td><span class="label label-danger" ng-click="vm.changeTakenNo(' + data.id + ')" ' +
                'style="cursor: pointer;" role="button" tabindex="0">No</span></td>';
        }

        function search() {
            vm.dtInstance.reloadData();
        }

        function prepareData(data) {
            for (var i = 0; i < data.length; i++) {
                data[i].prescriptionTime = moment(data[i].datePrescription).format('hh:mm a');
                data[i].prescriptionDate = moment(data[i].datePrescription).format('YYYY-MM-DD');
                data[i].chart.patient.fullNameCapital = CoreService.fullNameCapitalized(data[i].chart.patient);
            }
            var startTime = null;
            var endTime = null;
            for (var i = 0; i < data.length; i++) {
                startTime = moment(data[i].group_start_time).format('hh:mm:ss a');
                endTime = moment(data[i].group_end_time).format('hh:mm:ss a');
                data[i].rangeTime = startTime + ' to ' + endTime;
            }

        }


        vm.chartColors = ["#f39c12", "#999da3"];

        vm.myFormatter = function (input) {
            return input + '%';
        };


        function getDashboardReport() {

            var filter = {fId: vm.facility.id};

            Dashboard.dashboardReport(filter, function (result) {
                vm.dashboardReport = result;

                vm.dashboardReport.inProgressGroupSessionDetailsPerCent = vm.dashboardReport.inProgressGroupSessionDetails ?
                    ((vm.dashboardReport.inProgressGroupSessionDetails * 100) / vm.dashboardReport.totalGroupsForTheDay) : 0;


                vm.dashboardReport.pendingReviewGroupSessionDetailsPerCent = vm.dashboardReport.pendingReviewGroupSessionDetails ?
                    ((vm.dashboardReport.pendingReviewGroupSessionDetails * 100) / vm.dashboardReport.totalGroupsForTheDay) : 0;


                vm.dashboardReport.completedGroupSessionDetailsPerCent = vm.dashboardReport.completedGroupSessionDetails ?
                    ((vm.dashboardReport.completedGroupSessionDetails * 100) / vm.dashboardReport.totalGroupsForTheDay) : 0;

            });

        }

        /** Tuning **/
        function getPhysicianReview() {

            var filter = {fId: vm.facility.id};

            Dashboard.physicianReview(filter, function (result) {
                vm.physicianReview = result;
            });
        }

        function getUnassignedLabResult() {

            var filter = {fId: vm.facility.id};

            Dashboard.unassignedLabResult(filter, function (result) {
                vm.unassignedLabResult = result;
            });

        }

        function getUnsignedFormsCount() {

            var filter = {fId: vm.facility.id};

            Dashboard.unsignedFormsCount(filter, function (result) {
                vm.unsignedFormsCount = result;
            });

        }

        function getConcurrentsReviewCount() {

            var filter = {fId: vm.facility.id};

            Dashboard.concurrentsReviewCount(filter, function (result) {
                vm.concurrentsReviewCount = result;
            });

        }

        function getGroupSessionInProcessCount() {

            var filter = {fId: vm.facility.id};

            Dashboard.groupSessionInProcessCount(filter, function (result) {
                vm.groupSessionInProcessCount = result;

                //%
                if (vm.groupSessionTotalForTodayCount) {
                    vm.groupSessionInProcessCount.perCent = calPerCent(vm.groupSessionInProcessCount.value);
                }
            });

        }

        function getGroupSessionPendingReviewCount() {

            var filter = {fId: vm.facility.id};

            Dashboard.groupSessionPendingReviewCount(filter, function (result) {
                vm.groupSessionPendingReviewCount = result;

                //%
                if (vm.groupSessionTotalForTodayCount) {
                    vm.groupSessionPendingReviewCount.perCent = calPerCent(vm.groupSessionPendingReviewCount.value);
                }
            });

        }

        function getGroupSessionCompletedCount() {

            var filter = {fId: vm.facility.id};

            Dashboard.groupSessionCompletedCount(filter, function (result) {
                vm.groupSessionCompletedCount = result;

                if (vm.groupSessionTotalForTodayCount) {
                    vm.groupSessionCompletedCount.perCent = calPerCent(vm.groupSessionCompletedCount.value);
                }
            });

        }

        function getGroupSessionTotalForTodayCount() {

            var filter = {fId: vm.facility.id};

            Dashboard.groupSessionTotalForTodayCount(filter, function (result) {
                vm.groupSessionTotalForTodayCount = result;


                //%
                if (vm.groupSessionInProcessCount) {
                    vm.groupSessionInProcessCount.perCent = calPerCent(vm.groupSessionInProcessCount.value);
                }

                if (vm.groupSessionPendingReviewCount) {
                    vm.groupSessionPendingReviewCount.perCent = calPerCent(vm.groupSessionPendingReviewCount.value);
                }

                if (vm.groupSessionCompletedCount) {
                    vm.groupSessionCompletedCount.perCent = calPerCent(vm.groupSessionCompletedCount.value);
                }
            });

        }

        function calPerCent(number) {
            return Math.round((number * 100) / vm.groupSessionTotalForTodayCount.value);
        }


        function getIncomingPatients3DayNext() {

            var filter = {fId: vm.facility.id};

            Dashboard.incomingPatients3DayNext(filter, function (result) {
                vm.incomingPatients3DayNext = result;
            });

        }

        function getDischargingPatients3DayNext() {

            var filter = {fId: vm.facility.id};

            Dashboard.dischargingPatients3DayNext(filter, function (result) {
                vm.dischargingPatients3DayNext = result;
            });

        }


        function loadAllReportTuning() {

            getPhysicianReview();
            getUnassignedLabResult();
            getUnsignedFormsCount();
            getConcurrentsReviewCount();

            getGroupSessionInProcessCount();
            getGroupSessionPendingReviewCount();
            getGroupSessionCompletedCount();
            getGroupSessionTotalForTodayCount();

            getIncomingPatients3DayNext();
            getDischargingPatients3DayNext();

        }


    }


})();
