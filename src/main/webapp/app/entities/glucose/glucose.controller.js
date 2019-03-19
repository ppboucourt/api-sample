(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('GlucoseController', GlucoseController);

    GlucoseController.$inject = ['$scope', '$state', 'Glucose', 'GlucoseSearch', '$stateParams', '$q', 'DTOptionsBuilder',
    'DTColumnBuilder', '$filter', '$compile', '$rootScope', '$uibModal', 'lodash', 'DatePickerRangeOptions'];

    function GlucoseController ($scope, $state, Glucose, GlucoseSearch, $stateParams, $q, DTOptionsBuilder, DTColumnBuilder,
                                $filter, $compile, $rootScope, $uibModal, _, DatePickerRangeOptions) {
        var vm = this;

        vm.glucoses = [];
        vm.search = search;
        vm.$stateParams = $stateParams;

        vm.dateformat = 'MM/dd/yyyy HH:mm';
        vm.filterApplied = false;
        vm.allGlucoses = false;
        vm.showLastMeasure = true;
        vm.dtInstance = {};
        vm.datePickerRange = {startDate: null, endDate: null};
        vm.datePickerRangeOptions = angular.extend({}, DatePickerRangeOptions, {
            eventHandlers: {
                'apply.daterangepicker': function (ev, picker) {
                    // console.log("ev", ev.model);
                    if (ev.model) {
                        vm.filterGlucoseDate(ev.model.startDate, ev.model.endDate);
                    }
                }
            }
        });

        vm.filter={};

       vm.marginValues =  {
            top: 20,
            right: 100,
            bottom: 40,
            left: 80
        }

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function () {
            return vm.loadAll();
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip')
            .withOption('aaSorting', [[0, 'desc']])
            .withOption('fnRowCallback',
            function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
                $compile(nRow)($scope);
            });
        vm.dtColumns = [
            DTColumnBuilder.newColumn(null).withTitle('Date').withOption('width', '155px')
                .renderWith(function (data) {
                    var date = $filter('date')(data.createdDate, "MM/dd/yyyy hh:mm a");
                    return "<span class='badge label-info'>" + date + "</span>";
                }),
            DTColumnBuilder.newColumn('reading').withTitle('Reading'),
            DTColumnBuilder.newColumn('typeOfCheck').withTitle('Type of Check'),
            DTColumnBuilder.newColumn(null).withTitle('Interventions').renderWith(function (data) {
                return vm.captionGlucoseInterventions(data);
            }),
        ];

        vm.datePickerOpenStatusStart = {};
        vm.datePickerOpenStatusEnd = {};

        vm.openCalendarStart = openCalendarStart;
        vm.openCalendarEnd = openCalendarEnd;

        vm.datePickerOpenStatusStart.date = false;
        vm.datePickerOpenStatusStart.time = false;

        vm.datePickerOpenStatusEnd.date = false;
        vm.datePickerOpenStatusEnd.time = false;

        function openCalendarStart(date) {
            vm.datePickerOpenStatusStart[date] = true;
        }

        function openCalendarEnd(date) {
            vm.datePickerOpenStatusEnd[date] = true;
        }

        vm.addGlucoseMeasurement = function() {
            $uibModal.open({
                templateUrl: 'app/entities/glucose/glucose-dialog.html',
                controller: 'GlucoseDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    entity: function () {
                        return {
                            date: null,
                            time: null,
                            reading: null,
                            type_check: null,
                            intervention: null,
                            notes: null,
                            status: null,
                            typeOfCheck: null,
                            id: null,
                            chartId: vm.$stateParams.id
                        };
                    }
                }
            }).result.then(function() {
                vm.search();
            }, function() {
                //Error
            });
        }

        vm.filterGlucoseDate = function (start, end) {
            vm.glucoses = vm.inmutableGlucoses.filter(function (o) {
                // console.log("between", moment(o.date).isBetween(start, end));
                return moment(o.date).isBetween(start, end);
            });

            vm.calculateAllData();
            vm.reportDefaults();
        }


        vm.cleanFilterGlucoseDate = function () {
            vm.datePickerRange.startDate = null;
            vm.datePickerRange.endDate = null;
            $('#dateRangeGlucose').val(''); //Dont sync

            vm.glucoses = vm.inmutableGlucoses.slice();

            vm.calculateAllData();
            vm.reportDefaults();
        }


        vm.filterArray = function (data) {
            return !data.hidden;
        }

        // vm.must = [];
        //
        // vm.must.push({term: {"chartId": vm.$stateParams.id}});
        // vm.must.push({term: {"delStatus": false}});

        vm.loadAll = function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                Glucose.byChart({id: vm.$stateParams.id},function(result) {
                    vm.glucoses = _.sortBy(result, ['createdDate'], ['desc']);
                    // console.log("result", result);
                    defer.resolve(vm.glucoses);

                    vm.reading = [];

                    if( vm.glucoses &&  vm.glucoses.length==0){
                        vm.glucoses.push({reading:'N/A', typeOfCheck:'N/A', intervention:'N/A', hidden:true});
                    }else{
                        //dataGlucose
                        vm.inmutableGlucoses = vm.glucoses.slice();
                    }

                    vm.calculateAllData();
                    vm.reportDefaults();

                });
            }else {
                defer.resolve($filter('filter')(vm.glucoses, vm.searchQuery, undefined));
            }
            return defer.promise;
        }

        vm.calculateAllData =function(){

            vm.glucoses = vm.glucoses.sort(function(a,b){
                return new Date(b.date) - new Date(a.date);
            });


            vm.reading = [];
            for (var j = 0; j < vm.glucoses.length; j++) {
                var newDate = new Date(vm.glucoses[j].date);
                vm.reading.push({x: newDate, y: parseInt(vm.glucoses[j].reading)});
            }

        }

        vm.reportDefaults =function(){

            vm.dataGlucoseTmp = [
                {
                    values: vm.reading,      //values - represents the array of {x,y} data points
                    key: 'Glucose', //key  - the name of the series.
                    color: '#4131ff'  //color - optional: choose your own line color.
                }];

            vm.dataGlucose = vm.dataGlucoseTmp;
        }


        vm.captionGlucoseInterventions = function(data){
            var caption = 'N/A';
            if(vm.glucoses && vm.glucoses.length > 0) {
                var array = (data) ? data.glucoseInterventions : vm.glucoses[0].glucoseInterventions;
                if(array && array.length > 0){
                    caption = array[0].name;
                    for(var i=1; i< array.length; i++){
                        caption = caption + ', '+ array[i].name;
                    }
                }
            }
            return caption;
        }

        $scope.$on('$destroy', $rootScope.$on('bluebookApp:vitalsHistory', function (event, result) {
            var btnTarget = angular.element('div[data-role="glucose"]').find('button[data-widget="collapse"]')
            if (result.emitter !== 'glucose') {
                vm.viewGlucoseHistoryValue = false;
                vm.allGlucoses = false;
                vm.showLastMeasure = true;
                //Check if the section showed is last measure (default)
                if (result.showLastMeasure) {
                    //Check if the box is minimized to maximized
                    if (btnTarget.find('i.fa-plus').length > 0) {
                        btnTarget.click();
                    }
                } else {
                    if (btnTarget.find('i.fa-minus').length > 0) {
                        btnTarget.click();
                    }
                }
            }else {
                //Check if the box is minimized
                if (btnTarget.find('i.fa-plus').length > 0) {
                    btnTarget.click();
                }
            }
        }));

        vm.viewAllGlucoses = function () {
            if (vm.searchQuery) {
                vm.searchQuery = '';
                vm.search();
            }
            vm.allGlucoses = !vm.allGlucoses;
            //Hide graph
            vm.viewGlucoseHistoryValue = false;
            //Show default measures if the grid and the history are hidden
            vm.showLastMeasure = (!vm.allGlucoses && !vm.viewGlucoseHistoryValue) ? true : false;
            $scope.$emit('bluebookApp:vitalsHistory', {
                emitter: 'glucose',
                showLastMeasure: vm.showLastMeasure
            });
        }

        vm.viewGlucoseHistory = function () {
            vm.viewGlucoseHistoryValue = !vm.viewGlucoseHistoryValue;
            //Hide grid
            vm.allGlucoses = false;
            //Show default measures if the grid and the history are hidden
            vm.showLastMeasure = (!vm.allGlucoses && !vm.viewGlucoseHistoryValue) ? true : false;
            $scope.$emit('bluebookApp:vitalsHistory', {
                emitter: 'glucose',
                showLastMeasure: vm.showLastMeasure
            });
        }

        function search() {
            vm.dtInstance.reloadData();
        }

        vm.optionsGlucose = {
            refreshDataOnly: true,
            chart: {
                type: 'lineChart',
                height: 300,
                margin: vm.marginValues,
                x: function (d) {
                    return d.x;
                },
                y: function (d) {
                    return d.y;
                },
                useInteractiveGuideline: true,
                dispatch: {
                    stateChange: function (e) {
                        console.log("stateChange");
                    },
                    changeState: function (e) {
                        console.log("changeState");
                    },
                    tooltipShow: function (e) {
                        console.log("tooltipShow");
                    },
                    tooltipHide: function (e) {
                        console.log("tooltipHide");
                    }
                },
                xAxis: {
                    axisLabel: 'Time (days)',
                    tickFormat:(function(d) { return d3.time.format('%c')(new Date(d)); })
                },
                yAxis: {
                    axisLabel: 'mg/dl (s)',
                },
                callback: function (chart) {
                    // console.log("!!! lineChart callback !!!");
                }
            },
            title: {
                enable: true,
                html: '<b>History Glucose</b>'
            },
            subtitle: {
                enable: true,
                text: 'History Glucose in Blood in the last period of time',
                css: {
                    'text-align': 'center',
                    'margin': '10px 13px 0px 7px'
                }
            },
            caption: {
                enable: true,
                html: '<b>Figure 1.</b> Legend',
                css: {
                    'text-align': 'justify',
                    'margin': '10px 13px 0px 7px'
                }
            }
        };

        // vm.loadAll();
    }


})();
