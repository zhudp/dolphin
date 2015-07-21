Ext.namespace('Ext.ux');

/**
 * @class     Ext.ux.MonthPicker
 * @extends   Ext.Component
 * @param     {Object} config configuration object
 * @constructor
 */
Ext.ux.YearPicker = function(config) {
    Ext.apply(this, config);
    Ext.ux.YearPicker.superclass.constructor.call(this);
};

Ext.ux.YearPicker = Ext.extend(Ext.Component, {

    format : "Y",

    okText : Ext.MessageBox.buttonText.ok,

    cancelText : Ext.MessageBox.buttonText.cancel,

    constrainToViewport : true,

    yearNames : Date.yearNames,

    startDay : 0,

    value : 0,

    noPastYears: false, // only use the current year and future years

    useDayDate : null, // set to a number between 1-31 to use this day when creating the resulting date object (or null to use todays date or keep existing)

    initComponent: function(){
        Ext.ux.YearPicker.superclass.initComponent.call(this);

        this.value = this.value ?
                 this.value.clearTime() : new Date().clearTime();
        this.activeDate = this.value;

        this.addEvents(

            'select'
        );

        if(this.handler){
            this.on("select", this.handler,  this.scope || this);
        }
    },

    focus : function(){
        if(this.el){
            this.update(this.activeDate);
        }
    },

    onRender : function(container, position){
        var m = [ '<div style="width: 127px; height:150px;"></div>' ]
        m[m.length] = '<div class="x-date-mp"></div>';

        var el = document.createElement("div");
        el.className = "x-date-picker";
        el.innerHTML = m.join("");

        container.dom.insertBefore(el, position);

        this.el = Ext.get(el);
        this.yearPicker = this.el.down('div.x-date-mp');
        this.yearPicker.enableDisplayMode('block');

        this.el.unselectable();

        this.showYearPicker();

        if(Ext.isIE){
            this.el.repaint();
        }

        this.update(this.value);

    },

    createYearPicker : function(){

        if(!this.yearPicker.dom.firstChild){
            var buf = ['<table border="0" cellspacing="0">'];
            for(var i = 0; i < 6; i++){
                buf.push(
                    '<tr>',
                    i == 0 ?
                    '<td class="x-date-mp-ybtn" align="center"><a class="x-date-mp-prev"></a></td><td class="x-date-mp-ybtn" align="center"><a class="x-date-mp-next"></a></td></tr>' :
                    '<td class="x-date-mp-year"><a href="#"></a></td><td class="x-date-mp-year"><a href="#"></a></td></tr>'
                );
            }
            buf.push(
                '</table>'
            );
            this.yearPicker.update(buf.join(''));
            this.yearPicker.on('click', this.onYearClick, this);
            this.yearPicker.on('dblclick', this.onYearDblClick, this);

            this.mpYears = this.yearPicker.select('td.x-date-mp-year');
        }
    },

    showYearPicker : function(){
        this.createYearPicker();
        var size = this.el.getSize();
        this.yearPicker.setSize(size);
        this.yearPicker.child('table').setSize(size);

        this.mpSelYear = (this.activeDate || this.value).getFullYear();
        this.updateMPYear(this.mpSelYear);

        this.yearPicker.show();
        //this.monthPicker.slideIn('t', {duration:.2});
    },

    updateYearPicker: function() {
        if ((this.activeDate && !isNaN(this.activeDate.getElapsed())) || (this.value && !isNaN(this.value.getElapsed))) {
            this.mpSelYear = (this.activeDate || this.value || new Date()).getFullYear();
            this.updateMPYear(this.mpSelYear);
        }
    },

    updateMPYear : function(y){

        if ( this.noPastYears ) {
            var minYear = new Date().getFullYear();
            if ( y < (minYear+4) ) {
                y = minYear+4;
            }
        }

        this.mpyear = y;
        var ys = this.mpYears.elements;
        for(var i = 1; i <= 10; i++){
            var td = ys[i-1], y2;
            if((i%2) == 0){
                y2 = y + Math.round(i * .5);
                td.firstChild.innerHTML = y2;
                td.xyear = y2;
            }else{
                y2 = y - (5-Math.round(i * .5));
                td.firstChild.innerHTML = y2;
                td.xyear = y2;
            }

            this.mpYears.item(i-1)[y2 == this.mpSelYear ? 'addClass' : 'removeClass']('x-date-mp-sel');
        }
    },

    getAdjustedDate : function (year,month){
        return new Date(
            year,
            month,
            this.useDayDate ? // use a specific day date?
            (Math.min(this.useDayDate, (new Date(year, month, 1)).getDaysInMonth()))
            :
            (this.activeDate || this.value).getDate() // keep existing
        );
    },

    onYearClick : function(e, t){
        e.stopEvent();
        var el = new Ext.Element(t), pn;
        // now following the other handling (from original implementation)
        if(pn = el.up('td.x-date-mp-year', 2)){
            this.mpYears.removeClass('x-date-mp-sel');
            pn.addClass('x-date-mp-sel');
            this.mpSelYear = pn.dom.xyear;
            this.update(this.getAdjustedDate(pn.dom.xyear, 1));//this.mpSelMonth======================
        	this.fireEvent("select", this, this.value, this.oldValue);
        }
        else if(el.is('a.x-date-mp-prev')){
            this.updateMPYear(this.mpyear-10);
        }
        else if(el.is('a.x-date-mp-next')){
            this.updateMPYear(this.mpyear+10);
        }
    },

    onYearDblClick : function(e, t){
    	//保留功能
    },

    hideYearPicker : function(disableAnim){
        Ext.menu.MenuMgr.hideAll();
    },

    showPrevYear : function(){
        this.update(this.activeDate.add("y", -1));
    },

    showNextYear : function(){
        this.update(this.activeDate.add("y", 1));
    },

    update : function( date ) {
        this.activeDate = date;
        this.oldValue = this.value || date; // remember the old value
        this.value = date;

        if(!this.internalRender){
            var main = this.el.dom.firstChild;
            var w = main.offsetWidth;
            this.el.setWidth(w + this.el.getBorderWidth("lr"));
            Ext.fly(main).setWidth(w);
            this.internalRender = true;

            if(Ext.isOpera && !this.secondPass){
                main.rows[0].cells[1].style.width = (w - (main.rows[0].cells[0].offsetWidth+main.rows[0].cells[2].offsetWidth)) + "px";
                this.secondPass = true;
                this.update.defer(10, this, [date]);
            }
        }
    },

    setValue : function( date ) {
        if (date == 'Invalid Date') {
            this.activeDate = null;
            this.value = null;
        } else {
            this.activeDate = date;
            this.value = date;
        }
    }

});

Ext.reg('ux.yearpicker', Ext.ux.YearPicker);

Ext.ux.YearItem = function(config){
    Ext.ux.YearItem.superclass.constructor.call(this, new Ext.ux.YearPicker(config), config);

    this.picker = this.component;
    this.addEvents('select');

    this.picker.on("render", function(picker){
        picker.getEl().swallowEvent("click");
        picker.container.addClass("x-menu-date-item");
    });

    this.picker.on("select", this.onSelect, this, this.picker.value, this.picker.oldValue);
};

Ext.extend(Ext.ux.YearItem, Ext.menu.Adapter, {
    onSelect : function(picker, date, value, oldValue){
        this.fireEvent("select", this, date, picker, value, oldValue);
        Ext.ux.YearItem.superclass.handleClick.call(this);
    }
});

Ext.reg('ux.yearitem', Ext.ux.YearItem);

Ext.ux.YearMenu = function(config){
    Ext.ux.YearMenu.superclass.constructor.call(this, config);
    this.plain = true;
    var mi = new Ext.ux.YearItem(config);
    this.add(mi);

    this.picker = mi.picker;

    this.relayEvents(mi, ["select"]);
};

Ext.extend(Ext.ux.YearMenu, Ext.menu.Menu, {
    cls:'x-date-menu',

    /**
     * (Pre-)Set the date.
     */
    setDate: function(d) {
        this.picker.setValue(d);
    },

    setMinDate: function(d) {
        this.picker.minDate = d;
    },

    setMaxDate: function(d) {
        this.picker.maxDate = d;
    }

});

Ext.reg('ux.yearmenu', Ext.ux.YearMenu);

Ext.ux.YearField = function(config){
    Ext.ux.YearField.superclass.constructor.call(this, config);
}

Ext.extend(Ext.ux.YearField, Ext.form.DateField, {
    format : Ext.ux.YearPicker.prototype.format,
    noPastYears: Ext.ux.YearPicker.prototype.noPastYears,
    useDayDate: Ext.ux.YearPicker.prototype.useDayDate,
    beforeBlur : Ext.emptyFn,
    onTriggerClick : function(){
        if(this.disabled){
            return;
        }
        if(this.menu == null){
            this.menu = new Ext.ux.YearMenu();
        }
        Ext.apply(this.menu.picker, {
            format : this.format,
            noPastYears : this.noPastYears,
            useDayDate : this.useDayDate
        });
        this.menu.on(Ext.apply({}, this.menuListeners, {
            scope:this
        }));
        this.menu.picker.setValue(this.getValue() || new Date());
        this.menu.show(this.el, "tl-bl?");
    }
});
