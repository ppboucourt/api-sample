(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('VitalsController', VitalsController);

    VitalsController.$inject = ['$state', 'Vitals', '$q', 'DTOptionsBuilder', 'DTColumnBuilder', 'GUIUtils', '$filter',
        '$stateParams', 'VitalsSearch', '$scope', '$compile', '$rootScope', '$uibModal', 'lodash', 'DatePickerRangeOptions'];

    function VitalsController($state, Vitals, $q, DTOptionsBuilder, DTColumnBuilder, GUIUtils, $filter, $stateParams,
                              VitalsSearch, $scope, $compile, $rootScope, $uibModal, _, DatePickerRangeOptions) {
        var vm = this;
        vm.viewHistory = false;
        vm.allVitals = false;
        vm.showLastMeasure = true;
        vm.$stateParams = $stateParams;
        vm.startDate = new Date();
        vm.endDate = new Date();
        vm.datePickerRange = {startDate: null, endDate: null};
        vm.datePickerRangeOptions = angular.extend({}, DatePickerRangeOptions, {
            eventHandlers: {
                'apply.daterangepicker': function (ev, picker) {
                    // console.log("ev", ev.model);
                    if (ev.model) {
                        vm.filterDate(ev.model.startDate, ev.model.endDate);
                    }
                }
            }
        });

        vm.dateformat = 'MM/dd/yyyy HH:mm';
        vm.filterApplied = false;
        vm.dtInstance = {};

        vm.vitals = [];
        vm.search = search;

        vm.datePickerOpenStatusStart = {};
        vm.datePickerOpenStatusEnd = {};

        vm.openCalendarStart = openCalendarStart;
        vm.openCalendarEnd = openCalendarEnd;

        vm.datePickerOpenStatusStart.date = false;
        vm.datePickerOpenStatusStart.time = false;

        vm.datePickerOpenStatusEnd.date = false;
        vm.datePickerOpenStatusEnd.time = false;

        vm.typeReports = [{id: 1, name: "BP Pressure"}, {id: 2, name: "Temperature"}, {id: 3, name: "Pulse"},
            {id: 4, name: "Respiration"}, {id: 5, name: "O2 Saturation"}];

        vm.typeReport = vm.typeReports[0];

        vm.marginValues = {
            top: 20,
            right: 100,
            bottom: 40,
            left: 80
        }

        $scope.$on('$destroy', $rootScope.$on('bluebookApp:vitalsHistory', function (event, result) {
            var btnTarget = angular.element('div[data-role="vital"]').find('button[data-widget="collapse"]')
            if (result.emitter !== 'vital') {
                vm.viewHistory = false;
                vm.allVitals = false;
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
            } else {
                //Check if the box is minimized
                if (btnTarget.find('i.fa-plus').length > 0) {
                    btnTarget.click();
                }
            }
        }));

        vm.viewAllVitals = function () {
            if (vm.searchQuery) {
                vm.searchQuery = '';
                vm.search();
            }
            vm.allVitals = !vm.allVitals;
            //Hide graph
            vm.viewHistory = false;
            //Show default measures if the grid and the history are hidden
            vm.showLastMeasure = (!vm.allVitals && !vm.viewHistory) ? true : false;
            $scope.$emit('bluebookApp:vitalsHistory', {
                emitter: 'vital',
                showLastMeasure: vm.showLastMeasure
            });
        }

        vm.viewVitalsHistory = function () {
            vm.viewHistory = !vm.viewHistory;
            //Hide grid
            vm.allVitals = false;
            //Show default measures if the grid and the history are hidden
            vm.showLastMeasure = (!vm.allVitals && !vm.viewHistory) ? true : false;
            $scope.$emit('bluebookApp:vitalsHistory', {
                emitter: 'vital',
                showLastMeasure: vm.showLastMeasure
            });
        }

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function () {
            return vm.loadAll();
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip').withOption('aaSorting', [[0, 'desc']])
            .withOption('fnRowCallback', function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
                $compile(nRow)($scope);
            });

        vm.dtColumns = [
            DTColumnBuilder.newColumn(null).withTitle('Date').withOption('width', '155px')
                .renderWith(function (data) {
                    var date = $filter('date')(data.createdDate, "MM/dd/yyyy hh:mm a");
                    return "<span class='badge label-info'>" + date + "</span>";
                }),
            DTColumnBuilder.newColumn(null).withTitle('Blood Pressure').renderWith(function (data) {
                return data.bp_systolic + "/" + data.bp_diastolic;
            }),
            DTColumnBuilder.newColumn('temperature').withTitle('Temperature'),
            DTColumnBuilder.newColumn('pulse').withTitle('Pulse'),
            DTColumnBuilder.newColumn('respiration').withTitle('Respiration'),
            DTColumnBuilder.newColumn('o2_saturation').withTitle('O<sup>2</sup> Saturation')
        ];


        /****** Function*******/

        function openCalendarStart(date) {
            vm.datePickerOpenStatusStart[date] = true;
        }

        function openCalendarEnd(date) {
            vm.datePickerOpenStatusEnd[date] = true;
        }

        function search() {
            vm.dtInstance.reloadData();
        }

        vm.filterDate = function (start, end) {
            vm.vitals = vm.inmutableVitals.filter(function (o) {
                // console.log("between", moment(o.date).isBetween(start, end));
                return moment(o.date).isBetween(start, end);
            });
            vm.calculateAllData();
            vm.typeReportChange();
        }

        vm.cleanFilterDate = function () {
            vm.datePickerRange.startDate = null;
            vm.datePickerRange.endDate = null;
            $('#field_dateRange').val(''); //Dont sync

            vm.vitals = vm.inmutableVitals.slice();

            vm.calculateAllData();
            vm.typeReportChange();
        }

        vm.addVitalsMeasurement = function () {
            $uibModal.open({
                templateUrl: 'app/entities/vitals/vitals-dialog.html',
                controller: 'VitalsDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    entity: function () {
                        return {
                            date: null,
                            time: null,
                            bp_systolic: null,
                            bp_diastolic: null,
                            temperature: null,
                            pulse: null,
                            respiration: null,
                            o2_saturation: null,
                            status: null,
                            id: null,
                            chartId: vm.$stateParams.id
                        };
                    }
                }
            }).result.then(function () {
                vm.search();
            }, function () {
                //Error
            });
        }

        vm.filterArray = function (data) {
            return !data.hidden;
        }

        vm.loadAll = function () {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                Vitals.byChart({id: vm.$stateParams.id}, function (result) {
                    vm.vitals = _.sortBy(result, ['createdDate'], ['desc']);
                    // console.log("result", result);
                    defer.resolve(vm.vitals);
                    vm.searchQuery = null;

                    vm.inmutableVitals = vm.vitals.slice();// Inmutable copy

                    if (vm.vitals && vm.vitals.length == 0) {
                        vm.vitals.push({
                            bp_systolic: 'N',
                            bp_diastolic: 'A',
                            temperature: 'N/A',
                            pulse: 'N/A',
                            respiration: 'N/A',
                            o2_saturation: 'N/A',
                            hidden: true
                        });
                    }

                    vm.optionsPressure = {
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
                                tickFormat: (function (d) {
                                    return d3.time.format('%c')(new Date(d));
                                })
                            },
                            yAxis: {
                                axisLabel: 'mmHg (s)',
                            },
                            callback: function (chart) {
                                // console.log("!!! lineChart callback !!!");
                            }
                        },
                        title: {
                            enable: true,
                            html: '<b>History Blood Pressure</b>'
                        },
                        subtitle: {
                            enable: true,
                            text: 'History Blood Pressure in the last period of time',
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
                    vm.options = vm.optionsPressure; //by default

                    vm.optionsTemperature = {
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
                                tickFormat: (function (d) {
                                    return d3.time.format('%c')(new Date(d));
                                })
                            },
                            yAxis: {
                                axisLabel: 'F (s)',
                            },
                            callback: function (chart) {
                                console.log("!!! lineChart callback !!!");
                            }
                        },
                        title: {
                            enable: true,
                            html: '<b>History Temperature</b>'
                        },
                        subtitle: {
                            enable: true,
                            text: 'History Temperature in the last time period',
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

                    vm.optionsPulse = {
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
                                tickFormat: (function (d) {
                                    return d3.time.format('%c')(new Date(d));
                                })
                            },
                            yAxis: {
                                axisLabel: 'Count (s)',
                                /* tickFormat: function(d){
                                 return d3.format('.02f')(d);
                                 },*/

                                //axisLabelDistance: -10
                            },
                            callback: function (chart) {
                                console.log("!!! lineChart callback !!!");
                            }
                        },
                        title: {
                            enable: true,
                            html: '<b>History Pulse</b>'
                        },
                        subtitle: {
                            enable: true,
                            text: 'History Pulse in the last time period',
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

                    vm.optionsRespiration = {
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
                                tickFormat: (function (d) {
                                    return d3.time.format('%b %d %e')(new Date(d));
                                })
                            },
                            yAxis: {
                                axisLabel: 'Count (s)',

                            },
                            callback: function (chart) {
                                console.log("!!! lineChart callback !!!");
                            }
                        },
                        title: {
                            enable: true,
                            html: '<b>History Respiration</b>'
                        },
                        subtitle: {
                            enable: true,
                            text: 'History Respiration in the last time period',
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

                    vm.optionsSaturation = {
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
                                tickFormat: (function (d) {
                                    return d3.time.format('%c')(new Date(d));
                                })
                            },
                            yAxis: {
                                axisLabel: 'Per Cent (%)',
                            },
                            callback: function (chart) {
                                console.log("!!! lineChart callback !!!");
                            }
                        },
                        title: {
                            enable: true,
                            html: '<b>History Saturation</b>'
                        },
                        subtitle: {
                            enable: true,
                            text: 'History Saturation in the last time period',
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

                    vm.calculateAllData();
                    vm.setReportDefaults();

                });
            } else {
                defer.resolve($filter('filter')(vm.vitals, vm.searchQuery, undefined));
            }

            return defer.promise;
        }

        /*Random Data Generator */
        vm.calculateAllData = function () {

            vm.systolic = [];
            vm.diastolic = [];
            vm.temperature = [];
            vm.pulse = [];
            vm.respiration = [];
            vm.saturation = [];

            vm.vitals = vm.vitals.sort(function (a, b) {
                return new Date(b.date) - new Date(a.date);
            });

            for (var j = 0; j < vm.vitals.length; j++) {

                var newDate = new Date(vm.vitals[j].date);

                vm.systolic.push({x: newDate, y: parseInt(vm.vitals[j].bp_systolic)});
                vm.diastolic.push({x: newDate, y: parseInt(vm.vitals[j].bp_diastolic)});//*
                vm.temperature.push({x: newDate, y: parseInt(vm.vitals[j].temperature)});
                vm.pulse.push({x: newDate, y: parseInt(vm.vitals[j].pulse)});
                vm.respiration.push({x: newDate, y: parseInt(vm.vitals[j].respiration)});
                vm.saturation.push({x: newDate, y: parseInt(vm.vitals[j].o2_saturation)});
            }

        };

        vm.setReportDefaults = function () {
            vm.data = [
                {
                    values: vm.systolic,      //values - represents the array of {x,y} data points
                    key: 'BP Systolic', //key  - the name of the series.
                    color: '#ff7f0e'  //color - optional: choose your own line color.
                },
                {
                    values: vm.diastolic,
                    key: 'BP Diastolic',
                    color: '#2ca02c'
                }]; // By default
        }

        vm.typeReportChange = function () {

            switch (vm.typeReport.id) {
                case 1: {
                    vm.dataTemp = [
                        {
                            values: vm.systolic,      //values - represents the array of {x,y} data points
                            key: 'BP Systolic', //key  - the name of the series.
                            color: '#6129ff'  //color - optional: choose your own line color.
                        },
                        {
                            values: vm.diastolic,
                            key: 'BP Diastolic',
                            color: '#2ca02c'
                        }]; // By default


                    vm.data = vm.dataTemp;
                    vm.options = vm.optionsPressure;

                    break;

                }
                case 2: {
                    vm.dataTemp = [
                        {
                            values: vm.temperature,      //values - represents the array of {x,y} data points
                            key: 'Temperature', //key  - the name of the series.
                            color: '#ff7f0e'  //color - optional: choose your own line color.
                        }];

                    vm.data = vm.dataTemp;
                    vm.options = vm.optionsTemperature;
                    break;

                }
                case 3: {
                    vm.dataTemp = [
                        {
                            values: vm.pulse,      //values - represents the array of {x,y} data points
                            key: 'Pulse', //key  - the name of the series.
                            color: '#3b43ff'  //color - optional: choose your own line color.
                        }];

                    vm.data = vm.dataTemp;
                    vm.options = vm.optionsPulse;
                    break;

                }
                case 4: {
                    vm.dataTemp = [
                        {
                            values: vm.respiration,      //values - represents the array of {x,y} data points
                            key: 'Respirations', //key  - the name of the series.
                            color: '#ff1228'  //color - optional: choose your own line color.
                        }];

                    vm.data = vm.dataTemp;
                    vm.options = vm.optionsRespiration;
                    break;

                }
                case 5: {
                    vm.dataTemp = [
                        {
                            values: vm.saturation,      //values - represents the array of {x,y} data points
                            key: '02 Saturation', //key  - the name of the series.
                            color: '#19ff1a'  //color - optional: choose your own line color.
                        }];

                    vm.data = vm.dataTemp;
                    vm.options = vm.optionsSaturation;
                    break;

                }


            }
        }

        // vm.loadAll();

    }
})();
