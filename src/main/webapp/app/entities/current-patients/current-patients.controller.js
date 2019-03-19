(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('CurrentPatientController', CurrentPatientController)
        .filter('startFrom', function () {
            return function (input, start) {
                start = +start; //parse to int
                return input.slice(start);
            }
        });

    CurrentPatientController.$inject = ['$scope', '$state', '$stateParams', 'Chart', 'ChartSearch', 'PaginationUtil',
        'CoreService', 'ParseLinks', 'lodash', 'chart', 'DateUtils'];

    function CurrentPatientController($scope, $state, $stateParams, Chart, ChartSearch, PaginationUtil,
                                      CoreService, ParseLinks, _, chart, DateUtils) {
        var vm = this;
        vm.pagingParams = {
            page: PaginationUtil.parsePage($stateParams.page) || 0,
            sort: $stateParams.sort,
            predicate: ($stateParams.sort) ? PaginationUtil.parsePredicate($stateParams.sort) : true,
            ascending: ($stateParams.sort) ? PaginationUtil.parseAscending($stateParams.sort) : true,
            search: $stateParams.search,
            size: 24
        };

        //Variables
        var facility = CoreService.getCurrentFacility();
        vm.chart = chart;
        vm.currentCharts = [];
        vm.currentChartsCopy = [];
        $scope.currentPage = 0;
        $scope.pageSize = 18;
        $scope.today = new Date();
        $scope.dateformat = "MM/dd/yyyy";
        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.admissionDateFrom = false;
        vm.datePickerOpenStatus.admissionDateTo = false
        vm.datePickerOpenStatus.dischargeDateFrom = false;
        vm.datePickerOpenStatus.dischargeDateTo = false;
        vm.links = {};
        //  vm.typePatientProgram = TypePatientPrograms.query();
        vm.showFilter = false;
        vm.facility = CoreService.getCurrentFacility();
        vm.filters = {
            state: 'CURRENT',
            query: '',
            advancedSearch: false
        }

        //Functions
        vm.search = search;
        vm.loadAll = loadAll;
        vm.patientDetails = patientDetails;
        vm.popoverFilterEnable = popoverFilterEnable;
        vm.openCalendar = openCalendar;
        vm.filter = filter;
        vm.clear = clear;
        vm.onSelectPage = onSelectPage;
        vm.checkChartState = checkChartState;
        vm.filterState = filterState;

        loadAll();

        function onSelectPage(page) {
            vm.pagingParams.page = page - 1;
            loadAll(vm.filters.state);
        }

        function processCharts(result, headers) {
            // console.log("res", result);
            vm.currentCharts = result;
            CoreService.fullNameCapitalizedList(result);
            vm.emptyMssg = result.length <= 0;
            if (headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                vm.queryCount = vm.totalItems;
                vm.page = vm.pagingParams.page + 1;
            }
        }

        /**
         * Check chart state (ARCHIVE|CURRENT|WAITING)
         * @param chart
         * @returns {string}
         */
        function checkChartState(chart) {
            var state = '';
            if (moment().isAfter(moment(chart.dischargeDate).endOf('day'))) {
                state = 'ARCHIVE';
            }
            if (moment().isBefore(moment(chart.dischargeDate).endOf('day'))) {
                state = 'CURRENT';
            }
            if (chart.waitingRoom) {
                state = 'WAITING';
            }
            return state;
        }

        /**
         * Get elastic search filter
         * @returns {{must: Array, should: Array}}
         */
        function getQueryFilter() {
            var must = [];
            var should = [];
            must.push({term: {"facility.id": vm.facility.id}});
            must.push({term: {"delStatus": false}});
            if (vm.filters.state !== '*') {
                //Archive
                if (vm.filters.state === 'ARCHIVE') {
                    must.push({term: {"waitingRoom": false}});
                    must.push({range: {"dischargeDate": {lte: DateUtils.convertLocalDateToServer(moment().endOf('day').format())}}});
                }
                //Current patient
                if (vm.filters.state === 'CURRENT') {
                    must.push({term: {"waitingRoom": false}});
                    must.push({range: {"dischargeDate": {gte: DateUtils.convertLocalDateToServer(moment().endOf('day').format())}}});
                }
                //Waiting room
                if (vm.filters.state === 'WAITING') {
                    must.push({term: {"waitingRoom": true}});
                }
            }

            if (vm.filters.query) {
                should.push({regexp: {"patient.firstName": vm.filters.query.toLowerCase() + '.*'}});
                should.push({regexp: {"patient.lastName": vm.filters.query.toLowerCase() + '.*'}});
                should.push({regexp: {"mrNo": vm.filters.query + '.*'}});
                // should.push({match: {"mrNo": vm.filters.query}});
                // should.push({
                //     multi_match: {
                //         query: vm.filters.query,
                //         fields: ["patient.firstName", "patient.lastName"],
                //         fuzziness: 2
                //     }
                // })

            }
            //Popup filters
            if (vm.filters.firstName) {
                must.push({regexp: {"patient.firstName": vm.filters.firstName.toLowerCase() + '.*'}});
                // must.push({match: {"patient.firstName": {query: vm.filters.firstName, fuzziness: 2}}});
            }
            if (vm.filters.lastName) {
                must.push({regexp: {"patient.lastName": vm.filters.lastName.toLowerCase() + '.*'}});
                // must.push({match: {"patient.lastName": {query: vm.filters.lastName, fuzziness: 2}}});
            }
            if (vm.filters.admissionDate) {
                must.push({match: {"admissionDate": {query: vm.filters.admissionDate}}});
            }

            if (vm.filters.mrNo) {
                must.push({term: {mrNo: vm.filters.mrNo}});
            }
            if (vm.filters.typePatientProgram && vm.filters.typePatientProgram.id) {
                must.push({term: {"typePatientProgram.id": vm.filters.typePatientProgram.id}});
            }
            if (vm.filters.dischargeDateFrom) {
                must.push({range: {dischargeDate: {gte: DateUtils.convertLocalDateToServer(moment(vm.filters.dischargeDateFrom).endOf('day').format())}}});
            }
            if (vm.filters.dischargeDateTo) {
                must.push({range: {dischargeDate: {lte: DateUtils.convertLocalDateToServer(moment(vm.filters.dischargeDateTo).endOf('day').format())}}});
            }

            if (vm.filters.admissionDateFrom) {
                must.push({range: {admissionDate: {gte: DateUtils.convertLocalDateToServer(moment(vm.filters.admissionDateFrom).endOf('day').format())}}});
            }

            if (vm.filters.admissionDateTo) {
                must.push({range: {admissionDate: {lte: DateUtils.convertLocalDateToServer(moment(vm.filters.admissionDateTo).endOf('day').format())}}});
            }
            // console.log("vm.must", must);
            return {
                must: must,
                should: should,
                minimum_should_match: 1
            };
        }

        function filter() {
            vm.filters.advancedSearch = true;
            loadAll();
            popoverFilterEnable();
            clear();
        }

        function filterState(state) {
            vm.filters.state = state;
            loadAll(state)
        }

        vm.predicate = 'patient.firstName';
        vm.reverse = true;

        function loadAll(filterState) {
            if (((!vm.filters.query || vm.filters.query === '') && !vm.filters.advancedSearch) && !filterState) {
                // && vm.filters.state === 'CURRENT'
                vm.filters.state = 'CURRENT';
                Chart.allCharts({
                    id: facility.id,
                    page: vm.pagingParams.page,
                    size: vm.pagingParams.size,
                    query: vm.filters.query,
                    state: vm.filters.state,
                }, function (result, headers) {
                    processCharts(result, headers);
                });
            } else {
                vm.filters.state = (filterState) ? filterState : '*';
                var filter = {
                    sort: [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')],
                    "query": {
                        "filtered": {
                            "query": {
                                "match_all": {}
                            },
                            "filter": {
                                "bool": getQueryFilter()
                            }
                        }
                    },
                    page: vm.pagingParams.page,
                    size: vm.pagingParams.size
                };
                ChartSearch.allCharts(filter, function (result, headers) {
                    processCharts(result, headers);
                });
            }
        }

        function search() {
            loadAll();
        }

        function patientDetails(id) {
            $state.go('patient', {id: id});
        }

        function popoverFilterEnable() {
            vm.showFilter = !vm.showFilter;
        }


        function openCalendar(date) {
            vm.datePickerOpenStatus[date] = true;
        }

        /**
         * Clear advanced filter
         */
        function clear() {
            vm.filters = angular.extend({}, vm.filters, {
                firstName: null,
                lastName: null,
                mrNo: null,
                dischargeDateFrom: null,
                dischargeDateTo: null,
                admissionDateFrom: null,
                admissionDateTo: null,
                typePatientProgram: {},
                advancedSearch: false
            });
        }

    }

})();
